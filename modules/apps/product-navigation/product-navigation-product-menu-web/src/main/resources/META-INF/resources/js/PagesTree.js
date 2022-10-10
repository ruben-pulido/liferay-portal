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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useSessionState} from '@liferay/layout-content-page-editor-web';
import {fetch, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

export default function PagesTree({
	getAddChildCollectionURLTemplate,
	getAddChildURLTemplate,
	getConfigureLayoutURLTemplate,
	items,
	loadMoreItemsURL,
	maxPageSize,
	portletNamespace: namespace,
	selectedLayoutId,
}) {
	const onLoadMore = useCallback(
		(item, cursor = 1) =>
			fetch(loadMoreItemsURL, {
				body: Liferay.Util.objectToURLSearchParams({
					[`${namespace}parentLayoutId`]: item.layoutId,
					[`${namespace}selPlid`]: item.plid,
					[`${namespace}start`]: cursor * maxPageSize,
				}),
				method: 'post',
			})
				.then((response) => response.json())
				.then(({hasMoreElements, items: nextItems}) => {
					return {
						cursor: hasMoreElements ? cursor + 1 : null,
						items: nextItems,
					};
				})
				.catch(() => {
					openErrorToast();
				}),
		[loadMoreItemsURL, maxPageSize, namespace]
	);

	const [expandedKeys, setExpandedKeys] = useSessionState(
		`${namespace}_expandedKeys`,
		[]
	);

	return (
		<div className="pages-tree">
			<ClayTreeView
				defaultItems={items}
				displayType="dark"
				dragAndDrop
				expandedKeys={new Set(expandedKeys)}
				nestedKey="children"
				onExpandedChange={(keys) => {
					setExpandedKeys(Array.from(keys));
				}}
				onLoadMore={onLoadMore}
			>
				{(item, selection, expand, load) => (
					<ClayTreeView.Item>
						<ClayTreeView.ItemStack
							active={
								selectedLayoutId === item.id ? 'true' : null
							}
						>
							<ClayIcon symbol={item.icon} />

							<div className="align-items-center d-flex pl-2">
								<div className="flex-grow-1">
									<a href={item.regularURL}>{item.name}</a>
								</div>

								<ItemOptionsDropdown />
							</div>
						</ClayTreeView.ItemStack>

						<ClayTreeView.Group items={item.children}>
							{(item) => (
								<ClayTreeView.Item
									active={
										selectedLayoutId === item.id
											? 'true'
											: null
									}
								>
									<ClayIcon symbol={item.icon} />

									<div className="align-items-center d-flex pl-2">
										<div className="flex-grow-1">
											<a href={item.regularURL}>
												{item.name}
											</a>
										</div>

										<ItemOptionsDropdown />
									</div>
								</ClayTreeView.Item>
							)}
						</ClayTreeView.Group>

						{load.get(item.id) !== null &&
							expand.has(item.id) &&
							item.paginated && (
								<ClayButton
									borderless
									className="ml-3 text-light"
									displayType="secondary"
									onClick={() => load.loadMore(item.id, item)}
								>
									{Liferay.Language.get('load-more-results')}
								</ClayButton>
							)}
					</ClayTreeView.Item>
				)}
			</ClayTreeView>
		</div>
	);
}

PagesTree.propTypes = {
	items: PropTypes.array.isRequired,
	loadMoreItemsURL: PropTypes.string.isRequired,
	maxPageSize: PropTypes.number.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	selectedLayoutId: PropTypes.oneOf([PropTypes.string, PropTypes.number]),
};

function ItemOptionsDropdown() {
	const items = [
		{
			disabled: true,
			label: 'Test option 1',
		},
		{
			disabled: false,
			label: 'Test option 2',
		},
	];

	return (
		<ClayDropDownWithItems
			className="text-right"
			items={items}
			trigger={
				<ClayButtonWithIcon
					className="mr-2 text-white"
					displayType="unstyled"
					onClick={(event) => event.stopPropagation()}
					small
					symbol="ellipsis-v"
				/>
			}
		/>
	);
}

ItemOptionsDropdown.propTypes = {};

function openErrorToast() {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
}
