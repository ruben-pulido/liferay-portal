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

import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React from 'react';

export default function PagesTree() {
	const items = Array.from(Array(100).keys()).map((index) => ({
		children: [],
		id: index,
		name: `Page ${index}`,
		url: '',
	}));

	items.push({
		id: 'load-more',
	});

	const onLoadMore = () => {
		return new Promise((resolve, reject) => {
			setTimeout(() => {
				reject(new Error('Could not load more items'));
			}, 1000);
		}).catch((error) => {
			console.log(error);
		});
	};

	return (
		<div className="pages-tree">
			<ClayTreeView
				defaultItems={items}
				displayType="dark"
				expanderIcons={{
					close: <ClayIcon symbol="hr" />,
					open: <ClayIcon symbol="plus" />,
				}}
				nestedKey="children"
				onLoadMore={onLoadMore}
				showExpanderOnHover={false}
			>
				{(item) => {
					const hasUrl = item.url && item.url !== '#';

					if (item.id === 'load-more') {
						return (
							<ClayTreeView.Item>
								<ClayTreeView.ItemStack>
									<p className="m-0">Load more items</p>
								</ClayTreeView.ItemStack>
							</ClayTreeView.Item>
						);
					}

					return (
						<ClayTreeView.Item>
							<ClayTreeView.ItemStack>
								<ClayIcon
									symbol={item.url ? 'page' : 'folder'}
								/>

								{hasUrl ? (
									<a
										className="d-block h-100 w-100"
										href={item.url}
									>
										{item.name}
									</a>
								) : (
									<p className="m-0">{item.name}</p>
								)}
							</ClayTreeView.ItemStack>

							<ClayTreeView.Group items={item.children}>
								{(item) => (
									<ClayTreeView.Item>
										<ClayIcon
											symbol={
												item.url ? 'page' : 'folder'
											}
										/>

										{hasUrl ? (
											<a
												className="d-block h-100 w-100"
												href={item.url}
											>
												{item.name}
											</a>
										) : (
											<p className="m-0">{item.name}</p>
										)}
									</ClayTreeView.Item>
								)}
							</ClayTreeView.Group>
						</ClayTreeView.Item>
					);
				}}
			</ClayTreeView>
		</div>
	);
}
