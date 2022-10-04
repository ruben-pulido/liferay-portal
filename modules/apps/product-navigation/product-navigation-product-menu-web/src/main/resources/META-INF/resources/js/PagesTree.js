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

import ClayButton from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React from 'react';

const ITEMS = [
	{
		children: [
			{
				children: [
					{
						children: [{id: 17, name: 'Research 1'}],
						id: 3,
						name: 'Research',
					},
					{
						children: [{id: 16, name: 'News 1'}],
						id: 4,
						name: 'News',
					},
				],
				id: 2,
				name: 'Blogs',
				paginated: true,
			},
			{
				children: [
					{
						children: [
							{
								id: 18,
								name: 'Instructions.pdf',
							},
						],
						id: 15,
						name: 'PDF',
					},
					{
						children: [
							{
								id: 6,
								name: 'Treeview review.docx',
							},
							{
								id: 7,
								name: 'Heuristics Evaluation.docx',
							},
						],
						id: 8,
						name: 'Word',
					},
				],
				id: 5,
				name: 'Documents and Media',
				url: 'https://www.google.es',
			},
		],
		id: 1,
		name: 'Liferay Drive',
	},
	{
		children: [
			{id: 10, name: 'Blogs'},
			{id: 11, name: 'Documents and Media'},
		],
		id: 9,
		name: 'Repositories',
		paginated: true,
	},
	{
		children: [
			{id: 13, name: 'PDF'},
			{id: 14, name: 'Word'},
		],
		id: 12,
		name: 'Documents and Media',
	},
];

export default function PagesTree() {
	const onLoadMore = async (item, cursor = 1) => {
		if (!item.children) {
			return;
		}

		if (cursor === null) {
			return;
		}

		await new Promise((resolve) => {
			setTimeout(() => resolve(''), 1000);
		});

		const newCursor = cursor + 1;

		return {
			cursor: newCursor <= 3 ? newCursor : null,
			items: [
				{
					id: Math.random(),
					name: `${item.name} ${Math.random()}`,
				},
				{
					id: Math.random(),
					name: `${item.name} ${Math.random()}`,
				},
				{
					id: Math.random(),
					name: `${item.name} ${Math.random()}`,
				},
			],
		};
	};

	const onItemMove = (item, parentItem) => {
		console.log(item, parentItem);
	};

	return (
		<div className="pages-tree">
			<ClayTreeView
				defaultExpandedKeys={new Set(['pages', 1, 2])}
				defaultItems={[
					{
						children: ITEMS,
						id: 'pages',
						name: Liferay.Language.get('pages'),
					},
				]}
				displayType="dark"
				dragAndDrop
				nestedKey="children"
				onItemMove={onItemMove}
				onLoadMore={onLoadMore}
				showExpanderOnHover={false}
			>
				{(item, selection, expand, load) => {
					const hasUrl = item.url && item.url !== '#';

					return (
						<ClayTreeView.Item>
							<ClayTreeView.ItemStack active={item.id === 2}>
								<ClayIcon symbol={item.icon} />

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
										<ClayIcon symbol={item.icon} />

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

							{load.get(item.id) !== null &&
								expand.has(item.id) &&
								item.paginated && (
									<ClayButton
										borderless
										className="text-light"
										displayType="secondary"
										onClick={() =>
											load.loadMore(item.id, item)
										}
									>
										{Liferay.Language.get(
											'load-more-results'
										)}
									</ClayButton>
								)}
						</ClayTreeView.Item>
					);
				}}
			</ClayTreeView>
		</div>
	);
}
