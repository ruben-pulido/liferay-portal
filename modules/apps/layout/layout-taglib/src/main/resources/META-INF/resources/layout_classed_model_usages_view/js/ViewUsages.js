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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayList from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import {fetch, objectToFormData, openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import '../css/ViewUsages.scss';

export default function ViewUsages({
	getJournalArticleUsagesURL: getUsagesURL,
	journalArticleResourcePrimKey: articleId,
	portletNamespace,
}) {
	const [activePage, setActivePage] = useState(0);
	const [totalPages, setTotalPages] = useState(5);

	const [items, setItems] = useState([]);

	const onUsagesLoad = useCallback(
		(pageIndex) => {
			setItems([]);

			fetch(getUsagesURL, {
				body: objectToFormData({
					[`${portletNamespace}pageIndex`]: pageIndex,
					[`${portletNamespace}journalArticleResourcePrimKey`]: articleId,
				}),
				method: 'POST',
			})
				.then((response) => response.json())
				.then(({totalNumberOfPages, usages}) => {
					setItems(usages);
					setTotalPages(totalNumberOfPages);
				})
				.catch(() => openErrorToast());
		},
		[articleId, getUsagesURL, portletNamespace]
	);

	useEffect(() => {
		onUsagesLoad(0);
	}, [onUsagesLoad]);

	return (
		<div className="cadmin">
			<ClayList className="usages-list">
				{items.length ? (
					items.map(({id, name, type}) => (
						<ClayList.Item flex key={id}>
							<ClayList.ItemField className="p-0" expand>
								<ClayList.ItemTitle className="mb-2">
									{name}
								</ClayList.ItemTitle>

								<ClayList.ItemText className="text-secondary">
									{type}
								</ClayList.ItemText>
							</ClayList.ItemField>

							<ClayList.ItemField className="p-0">
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'view-usage'
									)}
									displayType="secondary"
									size="sm"
									symbol="view"
									title={Liferay.Language.get('view-usage')}
								/>
							</ClayList.ItemField>
						</ClayList.Item>
					))
				) : (
					<ClayLoadingIndicator className="my-7" size="sm" />
				)}
			</ClayList>

			{totalPages > 1 && (
				<ClayPaginationWithBasicItems
					active={activePage}
					ellipsisBuffer={2}
					ellipsisProps={{
						'aria-label': Liferay.Language.get('more'),
						'title': Liferay.Language.get('more'),
					}}
					onActiveChange={(nextPageIndex) => {
						setActivePage(nextPageIndex);
						onUsagesLoad(nextPageIndex);
					}}
					totalPages={totalPages}
				/>
			)}
		</div>
	);
}

function openErrorToast() {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
}
