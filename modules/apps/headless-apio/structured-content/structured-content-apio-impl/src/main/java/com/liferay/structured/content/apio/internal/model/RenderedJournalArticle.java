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

package com.liferay.structured.content.apio.internal.model;

import java.util.function.Supplier;

/**
 * @author Eduardo Perez
 */
public interface RenderedJournalArticle {

	public static RenderedJournalArticle create(
		Supplier<String> templateNameFunction,
		Supplier<String> renderedContentFunction) {

		return new RenderedJournalArticle() {

			@Override
			public String getRenderedContent() {
				return renderedContentFunction.get();
			}

			@Override
			public String getTemplateName() {
				return templateNameFunction.get();
			}

		};
	}

	public String getRenderedContent();

	public String getTemplateName();

}