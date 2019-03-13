/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.asset.internal.resource.v1_0;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.headless.asset.dto.v1_0.AdaptedImages;
import com.liferay.headless.asset.dto.v1_0.AssetListElement;
import com.liferay.headless.asset.dto.v1_0.BlogPosting;
import com.liferay.headless.asset.dto.v1_0.Categories;
import com.liferay.headless.asset.dto.v1_0.ContentDocument;
import com.liferay.headless.asset.dto.v1_0.ContentField;
import com.liferay.headless.asset.dto.v1_0.Document;
import com.liferay.headless.asset.dto.v1_0.Folder;
import com.liferay.headless.asset.dto.v1_0.Geo;
import com.liferay.headless.asset.dto.v1_0.Image;
import com.liferay.headless.asset.dto.v1_0.RenderedContents;
import com.liferay.headless.asset.dto.v1_0.StructuredContent;
import com.liferay.headless.asset.dto.v1_0.StructuredContentImage;
import com.liferay.headless.asset.dto.v1_0.StructuredContentLink;
import com.liferay.headless.asset.dto.v1_0.Value;
import com.liferay.headless.asset.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.asset.internal.dto.v1_0.util.ContentStructureUtil;
import com.liferay.headless.asset.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.asset.resource.v1_0.AssetListElementResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/asset-list-element.properties",
	scope = ServiceScope.PROTOTYPE, service = AssetListElementResource.class
)
public class AssetListElementResourceImpl
	extends BaseAssetListElementResourceImpl {

	@GET
	@Path("/asset-lists/{asset-list-id}/assets")
	@Produces("application/json")
	@Tags({@Tag(name = "AssetList")})
	public Page<AssetListElement> getAssetListElementPage(
			@PathParam("asset-list-id") Long assetListId,
			@Context Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.fetchAssetListEntry(assetListId);

		return Page.of(
			transform(
				assetListEntry.getAssetEntries(
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toAssetListElement),
			pagination, assetListEntry.getAssetEntriesCount());
	}

	private AdaptedImages[] _getAdaptiveMedias(FileEntry fileEntry)
		throws Exception {

		if (!_amImageMimeTypeProvider.isMimeTypeSupported(
				fileEntry.getMimeType())) {

			return new AdaptedImages[0];
		}

		Stream<AdaptiveMedia<AMImageProcessor>> stream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).withConfigurationStatus(
					AMImageQueryBuilder.ConfigurationStatus.ANY
				).done());

		return stream.map(
			this::_toAdaptedImages
		).toArray(
			AdaptedImages[]::new
		);
	}

	private List<String> _getFieldDisplayValues(Fields fields) {
		Field field = fields.get(DDM.FIELDS_DISPLAY_NAME);

		String fieldDisplayValue = (String)field.getValue();

		return ListUtil.toList(StringUtil.split(fieldDisplayValue));
	}

	private int _getFieldIndex(
		String fieldDisplayName, String fieldName, Fields fields) {

		List<String> fieldDisplayValues = _getFieldDisplayValues(fields);

		Stream<String> stream = fieldDisplayValues.stream();

		List<String> fieldValues = stream.filter(
			fieldDisplayValue -> fieldDisplayValue.startsWith(
				fieldName + DDM.INSTANCE_SEPARATOR)
		).collect(
			Collectors.toList()
		);

		return fieldValues.indexOf(fieldDisplayName);
	}

	private List<List<String>> _getFieldsDisplaySubstrings(
		String fieldName, List<String> fieldDisplayNames) {

		List<List<String>> substrings = new ArrayList<>();

		int offset = 0;

		for (int i = 1; i < fieldDisplayNames.size(); i++) {
			String firstString = StringUtil.extractFirst(
				fieldDisplayNames.get(i), DDM.INSTANCE_SEPARATOR);

			if (fieldName.equals(firstString)) {
				substrings.add(fieldDisplayNames.subList(offset, i));

				offset = i;
			}
		}

		substrings.add(
			fieldDisplayNames.subList(offset, fieldDisplayNames.size()));

		return substrings;
	}

	private Image _getImage(BlogsEntry blogsEntry) throws Exception {
		long coverImageFileEntryId = blogsEntry.getCoverImageFileEntryId();

		if (coverImageFileEntryId == 0) {
			return null;
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(coverImageFileEntryId);

		return new Image() {
			{
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
				imageId = coverImageFileEntryId;
				name = blogsEntry.getCoverImageCaption();
			}
		};
	}

	private <T, S> T _getValue(
		AdaptiveMedia<S> adaptiveMedia, AMAttribute<S, T> amAttribute) {

		Optional<T> optional = adaptiveMedia.getValueOptional(amAttribute);

		return optional.orElse(null);
	}

	private boolean _hasComments(BlogsEntry blogsEntry) {
		int count = _commentManager.getCommentsCount(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		if (count > 0) {
			return true;
		}

		return false;
	}

	private boolean _hasComments(JournalArticle journalArticle) {
		int count = _commentManager.getCommentsCount(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		if (count > 0) {
			return true;
		}

		return false;
	}

	private AdaptedImages _toAdaptedImages(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia) {

		return new AdaptedImages() {
			{
				contentUrl = String.valueOf(adaptiveMedia.getURI());
				height = _getValue(
					adaptiveMedia, AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);
				resolutionName = _getValue(
					adaptiveMedia,
					AMAttribute.getConfigurationUuidAMAttribute());
				sizeInBytes = _getValue(
					adaptiveMedia, AMAttribute.getContentLengthAMAttribute());
				width = _getValue(
					adaptiveMedia, AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);
			}
		};
	}

	private AssetListElement _toAssetListElement(AssetEntry inputAssetEntry)
		throws Exception {

		String className = inputAssetEntry.getClassName();

		AssetListElement assetListElement = null;

		if (className.equals(BlogPosting.class.getName())) {
			assetListElement = _toBlogPosting(
				_blogsEntryService.getEntry(inputAssetEntry.getClassPK()));
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			assetListElement = _toDocument(
				_dlAppService.getFileEntry(inputAssetEntry.getClassPK()));
		}
		else if (className.equals(Folder.class.getName())) {
			assetListElement = _toFolder(
				_dlAppService.getFolder(inputAssetEntry.getClassPK()));
		}
		else if (className.equals(JournalArticle.class.getName())) {
			assetListElement = toStructuredContent(
				_journalArticleService.getArticle(
					inputAssetEntry.getClassPK()));
		}
		else {
			assetListElement = new AssetListElement();
		}

		assetListElement.setAssetType(inputAssetEntry.getClassName());
		assetListElement.setOrder(inputAssetEntry.getPriority());

		return assetListElement;
	}

	private BlogPosting _toBlogPosting(BlogsEntry blogsEntry) throws Exception {
		return new BlogPosting() {
			{
				alternativeHeadline = blogsEntry.getSubtitle();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()));
				articleBody = blogsEntry.getContent();
				caption = blogsEntry.getCoverImageCaption();
				categories = transformToArray(
					_assetCategoryLocalService.getCategories(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()),
					assetCategory -> new Categories() {
						{
							categoryId = assetCategory.getCategoryId();
							categoryName = assetCategory.getName();
						}
					},
					Categories.class);
				contentSpace = blogsEntry.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(blogsEntry.getUserId()));
				dateCreated = blogsEntry.getCreateDate();
				dateModified = blogsEntry.getModifiedDate();
				datePublished = blogsEntry.getDisplayDate();
				description = blogsEntry.getDescription();
				encodingFormat = "text/html";
				friendlyUrlPath = blogsEntry.getUrlTitle();
				hasComments = _hasComments(blogsEntry);
				headline = blogsEntry.getTitle();
				id = blogsEntry.getEntryId();
				image = _getImage(blogsEntry);
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()),
					AssetTag.NAME_ACCESSOR);
			}
		};
	}

	private ContentField _toContentField(
			DDMFormField ddmFormField, DDMStructure ddmStructure, Field field,
			List<String> fieldDisplayValues, Fields fields)
		throws Exception {

		return new ContentField() {
			{
				dataType = ContentStructureUtil.toDataType(ddmFormField);
				inputControl = ContentStructureUtil.toInputControl(
					ddmFormField);
				name = field.getName();
				repeatable = field.isRepeatable();
				value = _toValue(
					field, fieldDisplayValues.get(0), fields,
					contextAcceptLanguage.getPreferredLocale());

				setNestedFields(
					() -> {
						if (fieldDisplayValues.size() <= 1) {
							return new ContentField[0];
						}

						return _toContentFields(
							ddmFormField.getNestedDDMFormFields(),
							ddmFormField -> _toContentFields(
								ddmStructure, fields, ddmFormField.getName(),
								fieldDisplayValues.subList(
									1, fieldDisplayValues.size())));
					});
			}
		};
	}

	private ContentField[] _toContentFields(
			DDMStructure ddmStructure, Fields fields, String fieldName,
			List<String> fieldDisplayValues)
		throws Exception {

		Field field = fields.get(fieldName);

		if (field == null) {
			return null;
		}

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(fieldName);

		if (ddmFormField.isRepeatable()) {
			return _toRepeatableContentField(
				ddmStructure, ddmFormField, fields, fieldDisplayValues);
		}

		return new ContentField[] {
			_toContentField(
				ddmFormField, ddmStructure, field, fieldDisplayValues, fields)
		};
	}

	private ContentField[] _toContentFields(JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Fields fields = _journalConverter.getDDMFields(
			ddmStructure, journalArticle.getContent());

		List<String> fieldDisplayValues = _getFieldDisplayValues(fields);

		return _toContentFields(
			ddmStructure.getRootFieldNames(),
			fieldName -> _toContentFields(
				ddmStructure, fields, fieldName, fieldDisplayValues));
	}

	private <T> ContentField[] _toContentFields(
		List<T> list,
		UnsafeFunction<T, ContentField[], Exception> unsafeFunction) {

		if (ListUtil.isEmpty(list)) {
			return new ContentField[0];
		}

		Stream<T> stream = list.stream();

		return stream.map(
			t -> {
				try {
					return unsafeFunction.apply(t);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		).filter(
			Objects::nonNull
		).flatMap(
			Stream::of
		).toArray(
			ContentField[]::new
		);
	}

	private Document _toDocument(FileEntry fileEntry) throws Exception {
		return _toDocument(
			fileEntry, fileEntry.getFileVersion(),
			_userLocalService.getUserById(fileEntry.getUserId()));
	}

	private Document _toDocument(
			FileEntry fileEntry, FileVersion fileVersion, User user)
		throws Exception {

		return new Document() {
			{
				adaptedImages = _getAdaptiveMedias(fileEntry);
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()));
				categories = transformToArray(
					_assetCategoryLocalService.getCategories(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()),
					assetCategory -> new Categories() {
						{
							categoryId = assetCategory.getCategoryId();
							categoryName = assetCategory.getName();
						}
					},
					Categories.class);
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileVersion, null, "");
				creator = CreatorUtil.toCreator(_portal, user);
				dateCreated = fileEntry.getCreateDate();
				dateModified = fileEntry.getModifiedDate();
				description = fileEntry.getDescription();
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				folderId = fileEntry.getFolderId();
				id = fileEntry.getFileEntryId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()),
					AssetTag.NAME_ACCESSOR);
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	private Folder _toFolder(
		com.liferay.portal.kernel.repository.model.Folder folder) {

		return new Folder() {
			{
				contentSpaceId = folder.getGroupId();
				dateCreated = folder.getCreateDate();
				dateModified = folder.getModifiedDate();
				description = folder.getDescription();
				id = folder.getFolderId();
				name = folder.getName();

				setHasDocuments(
					() -> {
						int count = _dlAppService.getFileEntriesCount(
							folder.getRepositoryId(), folder.getFolderId());

						return count > 0;
					});
				setHasFolders(
					() -> {
						int count = _dlAppService.getFoldersCount(
							folder.getRepositoryId(), folder.getFolderId());

						return count > 0;
					});
			}
		};
	}

	private ContentField[] _toRepeatableContentField(
			DDMStructure ddmStructure, DDMFormField ddmFormField, Fields fields,
			List<String> fieldDisplayValues)
		throws Exception {

		List<ContentField> contentFields = new ArrayList<>();

		List<List<String>> fieldsDisplaySubstrings =
			_getFieldsDisplaySubstrings(
				ddmFormField.getName(), fieldDisplayValues);

		for (List<String> substring : fieldsDisplaySubstrings) {
			contentFields.add(
				_toContentField(
					ddmFormField, ddmStructure,
					fields.get(ddmFormField.getName()), substring, fields));
		}

		return contentFields.toArray(new ContentField[0]);
	}

	protected StructuredContent toStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return new StructuredContent() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					journalArticle.getAvailableLanguageIds());
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()));
				categories = transformToArray(
					_assetCategoryLocalService.getCategories(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()),
					assetCategory -> new Categories() {
						{
							categoryId = assetCategory.getCategoryId();
							categoryName = assetCategory.getName();
						}
					},
					Categories.class);
				contentFields = _toContentFields(journalArticle);
				contentSpace = journalArticle.getGroupId();
				contentStructureId = ddmStructure.getStructureId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(journalArticle.getUserId()));
				dateCreated = journalArticle.getCreateDate();
				dateModified = journalArticle.getModifiedDate();
				datePublished = journalArticle.getDisplayDate();
				description = journalArticle.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				hasComments = _hasComments(journalArticle);
				id = journalArticle.getResourcePrimKey();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()),
					AssetTag.NAME_ACCESSOR);
				lastReviewed = journalArticle.getReviewDate();
				renderedContents = transformToArray(
					ddmStructure.getTemplates(),
					ddmTemplate -> new RenderedContents() {
						{
							renderedContentURL = getJAXRSLink(
								"getStructuredContentRenderedContentTemplate",
								journalArticle.getResourcePrimKey(),
								ddmTemplate.getTemplateId());
							templateName = ddmTemplate.getName(
								contextAcceptLanguage.getPreferredLocale());
						}
					},
					RenderedContents.class);
				title = journalArticle.getTitle(
					contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	private Value _toValue(
			Field field, String fieldDisplayValue, Fields fields, Locale locale)
		throws Exception {

		DDMStructure ddmStructure = field.getDDMStructure();

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(
			field.getName());

		String value = String.valueOf(
			field.getValue(
				locale,
				_getFieldIndex(fieldDisplayValue, field.getName(), fields)));

		if (Objects.equals(
				DDMFormFieldType.DOCUMENT_LIBRARY, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return null;
			}

			FileEntry fileEntry = _dlAppService.getFileEntry(classPK);

			return new Value() {
				{
					document = new ContentDocument() {
						{
							contentUrl = _dlURLHelper.getPreviewURL(
								fileEntry, fileEntry.getFileVersion(), null, "",
								false, false);
							encodingFormat = fileEntry.getMimeType();
							fileExtension = fileEntry.getExtension();
							id = fileEntry.getFileEntryId();
							sizeInBytes = fileEntry.getSize();
							title = fileEntry.getTitle();
						}
					};
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.GEOLOCATION, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			return new Value() {
				{
					geo = new Geo() {
						{
							latitude = jsonObject.getDouble("latitude");
							longitude = jsonObject.getDouble("longitude");
						}
					};
				}
			};
		}

		if (Objects.equals(DDMFormFieldType.IMAGE, ddmFormField.getType())) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			long fileEntryId = jsonObject.getLong("fileEntryId");

			if (fileEntryId == 0) {
				return null;
			}

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			return new Value() {
				{
					image = new StructuredContentImage() {
						{
							contentUrl = _dlURLHelper.getPreviewURL(
								fileEntry, fileEntry.getFileVersion(), null, "",
								false, false);
							description = jsonObject.getString("alt");
							encodingFormat = fileEntry.getMimeType();
							fileExtension = fileEntry.getExtension();
							id = fileEntry.getFileEntryId();
							sizeInBytes = fileEntry.getSize();
							title = fileEntry.getTitle();
						}
					};
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.JOURNAL_ARTICLE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return null;
			}

			JournalArticle journalArticle =
				_journalArticleService.getLatestArticle(classPK);

			return new Value() {
				{
					structuredContentLink = new StructuredContentLink() {
						{
							id = journalArticle.getId();
							title = journalArticle.getTitle();
						}
					};
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.LINK_TO_PAGE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			long layoutId = jsonObject.getLong("layoutId");

			if (layoutId == 0) {
				return null;
			}

			long groupId = jsonObject.getLong("groupId");
			boolean privateLayout = jsonObject.getBoolean("privateLayout");

			Layout layoutByUuidAndGroupId = _layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			return new Value() {
				{
					link = layoutByUuidAndGroupId.getFriendlyURL();
				}
			};
		}

		return new Value() {
			{
				data = value;
			}
		};
	}

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserLocalService _userLocalService;

}