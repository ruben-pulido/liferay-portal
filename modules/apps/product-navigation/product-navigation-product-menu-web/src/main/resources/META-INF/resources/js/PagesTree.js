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

// TODO:

// Decidir como queremos que se muestre el arbol la primera vez que carga (desplegado el primer nivel?)
// Decidir como queremos que se comporte cuando expandes / contraes nodos y recargas la pagina

// Decidir donde guardamos nodos expandidos

// Decidir si ponemos el root node en backend o en frontend
// Averiguar si es necesario mandar tantos datos


// Añadir URL templates a la config que pasa por props
// items, config

// No se muestran los nodos de tercer nivel

// Hay que mostrar Public Pages / Private Pages / ... como label del root node

// En el antiguo la primera vez que cargas el arbol solo salen desplegados los del primer nivel

// Context menu (drop down) para acciones. Ver como tienen que ser los URLs de la config y ver si podemos usar objectToURLSearchParams

import ClayButton from '@clayui/button';
import {TreeView as ClayTreeView} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {fetch} from 'frontend-js-web';

export default function PagesTree({items, loadMoreItemsURL, namespace}) {
	const onLoadMore = (item, cursor = 1) => {
		fetch(loadMoreItemsURL, {
			body: Liferay.Util.objectToURLSearchParams({
				[`${namespace}pageIndex`]: cursor,
				[`${namespace}start`]: cursor * 20,
				// [`pageIndex2`]: cursor,
				[`${namespace}selPlid`]: item.id,
				// [`selPlid2`]: item.id,
				[`${namespace}parentLayoutId`]: item.id,
				// [`parentLayoutId2`]: item.id,
			}),
			method: 'post',
		}).then((response) => {
			const {hasMoreElements, items: nextItems} = response;

			console.log(response);

			return {
				cursor: hasMoreElements ? cursor + 1 : null,
				items: nextItems,
			};
		});
	};

	const onItemMove = (item, parentItem) => {
		console.log(item, parentItem);
	};

	return (
		<div className="pages-tree">
			<ClayTreeView
				defaultExpandedKeys={new Set(['pages'])}
				defaultItems={[
					{
						children: items,
						id: 0,
						name: Liferay.Language.get('pages'),
						paginated: true,
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
							<ClayTreeView.ItemStack>
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
