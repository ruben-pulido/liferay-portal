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

package com.liferay.layout.page.template.headless.delivery.dto.v1_0;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.PageFragmentInstanceDefinition;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.converter.PageFragmentInstanceDefinitionDTOConverter;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Rubén Pulido
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *            PageFragmentInstanceDefinitionDTOConverter}
 */
@Deprecated
public class PageFragmentInstanceDefinitionConverterUtil {

	public static PageFragmentInstanceDefinition
		toPageFragmentInstanceDefinition(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
			FragmentLayoutStructureItem fragmentLayoutStructureItem,
			FragmentRendererTracker fragmentRendererTracker,
			InfoDisplayContributorTracker infoDisplayContributorTracker,
			boolean saveInlineContent, boolean saveMapping) {

		PageFragmentInstanceDefinitionDTOConverter
			pageFragmentInstanceDefinitionDTOConverter =
				_serviceTracker.getService();

		return pageFragmentInstanceDefinitionDTOConverter.toDTO(
			fragmentLayoutStructureItem, saveInlineContent, saveMapping);
	}

	public static PageFragmentInstanceDefinition
		toPageFragmentInstanceDefinition(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
			FragmentLayoutStructureItem fragmentLayoutStructureItem,
			FragmentRendererTracker fragmentRendererTracker,
			InfoDisplayContributorTracker infoDisplayContributorTracker,
			boolean saveInlineContent, boolean saveMapping,
			long segmentsExperienceId) {

		PageFragmentInstanceDefinitionDTOConverter
			pageFragmentInstanceDefinitionDTOConverter =
				_serviceTracker.getService();

		return pageFragmentInstanceDefinitionDTOConverter.toDTO(
			fragmentLayoutStructureItem, saveInlineContent, saveMapping);
	}

	public static PageFragmentInstanceDefinition
		toPageFragmentInstanceDefinition(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
			FragmentLayoutStructureItem fragmentLayoutStructureItem,
			InfoDisplayContributorTracker infoDisplayContributorTracker,
			FragmentRendererTracker fragmentRendererTracker) {

		PageFragmentInstanceDefinitionDTOConverter
			pageFragmentInstanceDefinitionDTOConverter =
				_serviceTracker.getService();

		return pageFragmentInstanceDefinitionDTOConverter.toDTO(
			fragmentLayoutStructureItem, true, true);
	}

	private static final ServiceTracker
		<PageFragmentInstanceDefinitionDTOConverter,
		 PageFragmentInstanceDefinitionDTOConverter> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PageFragmentInstanceDefinitionDTOConverter.class);

		ServiceTracker
			<PageFragmentInstanceDefinitionDTOConverter,
			 PageFragmentInstanceDefinitionDTOConverter> serviceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(),
					PageFragmentInstanceDefinitionDTOConverter.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}