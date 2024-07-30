/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.site.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.site.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.site.internal.resource.v1_0.DisplayPageTemplateFolderResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.DisplayPageTemplateResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.FragmentCompositionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.MasterPageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageElementResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageExperienceResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleActionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleConditionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageSpecificationResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageTemplateResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageTemplateSetResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.SitePageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.UtilityPageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.WidgetInstanceResourceImpl;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateFolderResource;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.headless.admin.site.resource.v1_0.FragmentCompositionResource;
import com.liferay.headless.admin.site.resource.v1_0.MasterPageResource;
import com.liferay.headless.admin.site.resource.v1_0.PageElementResource;
import com.liferay.headless.admin.site.resource.v1_0.PageExperienceResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleActionResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleConditionResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleResource;
import com.liferay.headless.admin.site.resource.v1_0.PageSpecificationResource;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateResource;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateSetResource;
import com.liferay.headless.admin.site.resource.v1_0.SitePageResource;
import com.liferay.headless.admin.site.resource.v1_0.UtilityPageResource;
import com.liferay.headless.admin.site.resource.v1_0.WidgetInstanceResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Rubén Pulido
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setDisplayPageTemplateResourceComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects);
		Mutation.setDisplayPageTemplateFolderResourceComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects);
		Mutation.setFragmentCompositionResourceComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects);
		Mutation.setMasterPageResourceComponentServiceObjects(
			_masterPageResourceComponentServiceObjects);
		Mutation.setPageElementResourceComponentServiceObjects(
			_pageElementResourceComponentServiceObjects);
		Mutation.setPageExperienceResourceComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects);
		Mutation.setPageRuleResourceComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects);
		Mutation.setPageRuleActionResourceComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects);
		Mutation.setPageRuleConditionResourceComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects);
		Mutation.setPageSpecificationResourceComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects);
		Mutation.setPageTemplateResourceComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects);
		Mutation.setPageTemplateSetResourceComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects);
		Mutation.setSitePageResourceComponentServiceObjects(
			_sitePageResourceComponentServiceObjects);
		Mutation.setUtilityPageResourceComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects);
		Mutation.setWidgetInstanceResourceComponentServiceObjects(
			_widgetInstanceResourceComponentServiceObjects);

		Query.setDisplayPageTemplateResourceComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects);
		Query.setDisplayPageTemplateFolderResourceComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects);
		Query.setFragmentCompositionResourceComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects);
		Query.setMasterPageResourceComponentServiceObjects(
			_masterPageResourceComponentServiceObjects);
		Query.setPageElementResourceComponentServiceObjects(
			_pageElementResourceComponentServiceObjects);
		Query.setPageExperienceResourceComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects);
		Query.setPageRuleResourceComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects);
		Query.setPageRuleActionResourceComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects);
		Query.setPageRuleConditionResourceComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects);
		Query.setPageSpecificationResourceComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects);
		Query.setPageTemplateResourceComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects);
		Query.setPageTemplateSetResourceComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects);
		Query.setSitePageResourceComponentServiceObjects(
			_sitePageResourceComponentServiceObjects);
		Query.setUtilityPageResourceComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects);
		Query.setWidgetInstanceResourceComponentServiceObjects(
			_widgetInstanceResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Admin.Site";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-admin-site-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createSiteSiteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteSiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"patchSiteSiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeDisplayPageTemplatePageSpecification",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteSiteExternalReferenceCodeDisplayPageTemplatePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"postSiteSiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"patchSiteSiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"postSiteSiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"postSiteSiteExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"patchSiteSiteExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"putSiteSiteExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postSiteSiteExternalReferenceCodeMasterPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeMasterPagePermissionsPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodeMasterPage"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"patchSiteSiteExternalReferenceCodeMasterPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeMasterPage"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeMasterPagePageSpecification",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postSiteSiteExternalReferenceCodeMasterPagePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeMasterPagePermissionsPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodePageElement"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"patchSiteSiteExternalReferenceCodePageElement"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageElement"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageElementFragmentComposition",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageElementFragmentComposition"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodePageExperience"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"patchSiteSiteExternalReferenceCodePageExperience"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageExperience"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageExperiencePageElement",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageExperiencePageElement"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageExperiencePageRule",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageExperiencePageRule"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodePageRule"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"patchSiteSiteExternalReferenceCodePageRule"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageRule"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageRulePageRuleAction",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageRulePageRuleAction"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageRulePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageRulePageRuleCondition"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodePageRuleAction"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"patchSiteSiteExternalReferenceCodePageRuleAction"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageRuleAction"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"patchSiteSiteExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodePageSpecification"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"patchSiteSiteExternalReferenceCodePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageSpecification"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageSpecificationPageExperience",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageSpecificationPageExperience"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageSpecificationPublish",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageSpecificationPublish"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageTemplate"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplatePermissionsPage",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodePageTemplate"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"patchSiteSiteExternalReferenceCodePageTemplate"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplate"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageTemplatePageSpecification",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageTemplatePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplatePermissionsPage",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"patchSiteSiteExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#createSiteSiteExternalReferenceCodePageTemplateSetPageTemplate",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"postSiteSiteExternalReferenceCodePageTemplateSetPageTemplate"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postSiteSiteExternalReferenceCodeSitePage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeSitePagePermissionsPage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodeSitePage"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"patchSiteSiteExternalReferenceCodeSitePage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeSitePage"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeSitePagePageSpecification",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postSiteSiteExternalReferenceCodeSitePagePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeSitePagePermissionsPage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeSitePageWidgetInstance",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postSiteSiteExternalReferenceCodeSitePageWidgetInstance"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postSiteSiteExternalReferenceCodeUtilityPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeUtilityPagePermissionsPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodeUtilityPage"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"patchSiteSiteExternalReferenceCodeUtilityPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeUtilityPage"));
					put(
						"mutation#createSiteSiteExternalReferenceCodeUtilityPagePageSpecification",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postSiteSiteExternalReferenceCodeUtilityPagePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeUtilityPagePermissionsPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"mutation#deleteSiteSiteExternalReferenceCodeWidgetInstance",
						new ObjectValuePair<>(
							WidgetInstanceResourceImpl.class,
							"deleteSiteSiteExternalReferenceCodeWidgetInstance"));
					put(
						"mutation#patchSiteSiteExternalReferenceCodeWidgetInstance",
						new ObjectValuePair<>(
							WidgetInstanceResourceImpl.class,
							"patchSiteSiteExternalReferenceCodeWidgetInstance"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeWidgetInstance",
						new ObjectValuePair<>(
							WidgetInstanceResourceImpl.class,
							"putSiteSiteExternalReferenceCodeWidgetInstance"));

					put(
						"query#siteExternalReferenceCodeDisplayPageTemplates",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplatesPage"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplatePermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplatePermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplateFolders",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplateFoldersPage"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplateFolderPermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplates",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplateFolderPermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"query#siteExternalReferenceCodeFragmentCompositions",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"getSiteSiteExternalReferenceCodeFragmentCompositionsPage"));
					put(
						"query#siteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"getSiteSiteExternalReferenceCodeFragmentComposition"));
					put(
						"query#siteExternalReferenceCodeMasterPages",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeMasterPagesPage"));
					put(
						"query#siteExternalReferenceCodeMasterPagePermissions",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"query#siteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeMasterPage"));
					put(
						"query#siteExternalReferenceCodeMasterPagePermissions",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"query#siteExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageElement"));
					put(
						"query#siteExternalReferenceCodePageElementPageElements",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageElementPageElementsPage"));
					put(
						"query#siteExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageExperience"));
					put(
						"query#siteExternalReferenceCodePageExperiencePageElements",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageExperiencePageElementsPage"));
					put(
						"query#siteExternalReferenceCodePageExperiencePageRules",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageExperiencePageRulesPage"));
					put(
						"query#siteExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageRule"));
					put(
						"query#siteExternalReferenceCodePageRulePageRuleActions",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageRulePageRuleActionsPage"));
					put(
						"query#siteExternalReferenceCodePageRulePageRuleConditions",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageRulePageRuleConditionsPage"));
					put(
						"query#siteExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageRuleAction"));
					put(
						"query#siteExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageRuleCondition"));
					put(
						"query#siteExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageSpecification"));
					put(
						"query#siteExternalReferenceCodePageSpecificationPageExperiences",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageSpecificationPageExperiencesPage"));
					put(
						"query#siteExternalReferenceCodePageTemplates",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplatesPage"));
					put(
						"query#siteExternalReferenceCodePageTemplatePermissions",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"query#siteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplate"));
					put(
						"query#siteExternalReferenceCodePageTemplatePermissions",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"query#siteExternalReferenceCodePageTemplateSets",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplateSetsPage"));
					put(
						"query#siteExternalReferenceCodePageTemplateSetPermissions",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"query#siteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplateSet"));
					put(
						"query#siteExternalReferenceCodePageTemplateSetPageTemplates",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplateSetPageTemplatesPage"));
					put(
						"query#siteExternalReferenceCodePageTemplateSetPermissions",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"query#siteExternalReferenceCodeSitePages",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePagesPage"));
					put(
						"query#siteExternalReferenceCodeSitePagePermissions",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"query#siteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePage"));
					put(
						"query#siteExternalReferenceCodeSitePageFriendlyUrlHistory",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePageFriendlyUrlHistoryPage"));
					put(
						"query#siteExternalReferenceCodeSitePagePageSpecifications",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePagePageSpecificationsPage"));
					put(
						"query#siteExternalReferenceCodeSitePagePermissions",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"query#siteExternalReferenceCodeSitePageWidgetInstances",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePageWidgetInstancesPage"));
					put(
						"query#siteExternalReferenceCodeUtilityPages",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeUtilityPagesPage"));
					put(
						"query#siteExternalReferenceCodeUtilityPagePermissions",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"query#siteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeUtilityPage"));
					put(
						"query#siteExternalReferenceCodeUtilityPagePageSpecifications",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeUtilityPagePageSpecificationsPage"));
					put(
						"query#siteExternalReferenceCodeUtilityPagePermissions",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"query#siteExternalReferenceCodeWidgetInstance",
						new ObjectValuePair<>(
							WidgetInstanceResourceImpl.class,
							"getSiteSiteExternalReferenceCodeWidgetInstance"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DisplayPageTemplateResource>
		_displayPageTemplateResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DisplayPageTemplateFolderResource>
		_displayPageTemplateFolderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FragmentCompositionResource>
		_fragmentCompositionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MasterPageResource>
		_masterPageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageElementResource>
		_pageElementResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageExperienceResource>
		_pageExperienceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleResource>
		_pageRuleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleActionResource>
		_pageRuleActionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleConditionResource>
		_pageRuleConditionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageSpecificationResource>
		_pageSpecificationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageTemplateResource>
		_pageTemplateResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageTemplateSetResource>
		_pageTemplateSetResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SitePageResource>
		_sitePageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<UtilityPageResource>
		_utilityPageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WidgetInstanceResource>
		_widgetInstanceResourceComponentServiceObjects;

}