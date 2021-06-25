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
import ClayLayout from '@clayui/layout';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';
import classNames from 'classnames';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {COLUMN_SIZE_MODULE_PER_ROW_SIZES} from '../../config/constants/columnSizes';
import {
	CollectionItemContext,
	CollectionItemContextProvider,
	useToControlsId,
} from '../../contexts/CollectionItemContext';
import {useIsActive} from '../../contexts/ControlsContext';
import {useDisplayPagePreviewItem} from '../../contexts/DisplayPagePreviewItemContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import CollectionService from '../../services/CollectionService';
import UnsafeHTML from '../UnsafeHTML';

const COLLECTION_ID_DIVIDER = '$';
const TOTAL_ENTRIES = 20;

function collectionIsMapped(collectionConfig) {
	return collectionConfig.collection;
}

function getCollectionPrefix(collectionId, index) {
	return `collection-${collectionId}-${index}${COLLECTION_ID_DIVIDER}`;
}

export function getToControlsId(collectionId, index, toControlsId) {
	return (itemId) => {
		if (!itemId) {
			return null;
		}

		return toControlsId(
			`${getCollectionPrefix(collectionId, index)}${itemId}`
		);
	};
}

export function fromControlsId(controlsItemId) {
	if (!controlsItemId) {
		return null;
	}

	const splits = controlsItemId.split(COLLECTION_ID_DIVIDER);

	const itemId = splits.pop();

	return itemId || controlsItemId;
}

const NotCollectionSelectedMessage = () => (
	<div className="page-editor__collection__not-collection-selected-message">
		{Liferay.Language.get('no-collection-selected-yet')}
	</div>
);

const Grid = ({
	child,
	collection,
	collectionConfig,
	collectionId,
	collectionLength,
	customCollectionSelectorURL,
}) => {
	const maxNumberOfItems = Math.min(
		collectionLength,
		collectionConfig.numberOfItems
	);
	const numberOfRows = Math.ceil(
		maxNumberOfItems / collectionConfig.numberOfColumns
	);

	return Array.from({length: numberOfRows}).map((_, i) => (
		<ClayLayout.Row key={`row-${i}`}>
			{Array.from({length: collectionConfig.numberOfColumns}).map(
				(_, j) => {
					const key = `col-${i}-${j}`;
					const index = i * collectionConfig.numberOfColumns + j;

					return (
						<ClayLayout.Col
							key={key}
							size={
								COLUMN_SIZE_MODULE_PER_ROW_SIZES[
									collectionConfig.numberOfColumns
								][collectionConfig.numberOfColumns][j]
							}
						>
							{index < maxNumberOfItems && (
								<ColumnContext
									collectionConfig={collectionConfig}
									collectionId={collectionId}
									collectionItem={collection[index]}
									customCollectionSelectorURL={
										customCollectionSelectorURL
									}
									index={index}
								>
									{React.cloneElement(child)}
								</ColumnContext>
							)}
						</ClayLayout.Col>
					);
				}
			)}
		</ClayLayout.Row>
	));
};

const ColumnContext = ({
	children,
	collectionConfig,
	collectionId,
	collectionItem,
	customCollectionSelectorURL,
	index,
}) => {
	const toControlsId = useToControlsId();

	const contextValue = useMemo(
		() => ({
			collectionConfig,
			collectionItem,
			collectionItemIndex: index,
			customCollectionSelectorURL,
			fromControlsId,
			parentToControlsId: toControlsId,
			toControlsId: getToControlsId(collectionId, index, toControlsId),
		}),
		[
			collectionConfig,
			collectionId,
			collectionItem,
			index,
			toControlsId,
			customCollectionSelectorURL,
		]
	);

	return (
		<CollectionItemContextProvider value={contextValue}>
			{children}
		</CollectionItemContextProvider>
	);
};

const DEFAULT_COLLECTION = {
	items: [{defaultTitle: Liferay.Language.get('title')}],
	length: 1,
};

const CollectionPagination = ({
	collectionConfig,
	collectionId,
	paginationType,
}) => {
	const [activePage, setActivePage] = useState(1);
	const isActive = useIsActive();

	const totalPages = Math.ceil(
		TOTAL_ENTRIES / collectionConfig.numberOfItemsPerPage
	);

	const onActivePage = (direction) => {
		setActivePage(
			direction === 'previous' ? activePage - 1 : activePage + 1
		);
	};

	return (
		<div
			className={classNames('page-editor__collection__pagination', {
				'page-editor__collection__pagination__overlay': !isActive(
					collectionId
				),
			})}
		>
			{paginationType === 'regular' ? (
				<ClayPaginationBar>
					<ClayPaginationBar.Results>
						{Liferay.Util.sub(
							Liferay.Language.get('showing-x-to-x-of-x-entries'),
							[
								collectionConfig.numberOfItemsPerPage *
									(activePage - 1) || 1,
								collectionConfig.numberOfItemsPerPage *
									activePage,
								TOTAL_ENTRIES,
							]
						)}
					</ClayPaginationBar.Results>

					<ClayPaginationWithBasicItems
						activePage={activePage}
						onPageChange={setActivePage}
						totalPages={totalPages}
					/>
				</ClayPaginationBar>
			) : (
				<div className="page-editor__collection__pagination--simple">
					<ClayButton
						disabled={activePage === 1}
						displayType="unstyled"
					>
						<span
							className="c-inner"
							onClick={() => onActivePage('previous')}
							tabIndex="-1"
						>
							{Liferay.Language.get('previous')}
						</span>
					</ClayButton>
					<ClayButton
						disabled={activePage === totalPages}
						displayType="unstyled"
						onClick={() => onActivePage('next')}
					>
						<span className="c-inner" tabIndex="-1">
							{Liferay.Language.get('next')}
						</span>
					</ClayButton>
				</div>
			)}
		</div>
	);
};

const Collection = React.forwardRef(({children, item}, ref) => {
	const activePage = 1;
	const child = React.Children.toArray(children)[0];
	const collectionConfig = item.config;

	const dispatch = useDispatch();
	const languageId = useSelector(selectLanguageId);

	const [collection, setCollection] = useState(DEFAULT_COLLECTION);

	const context = useContext(CollectionItemContext);
	const {classNameId, classPK} = context.collectionItem || {};

	const displayPagePreviewItemData = useDisplayPagePreviewItem()?.data ?? {};

	const itemClassNameId =
		classNameId || displayPagePreviewItemData.classNameId;
	const itemClassPK = classPK || displayPagePreviewItemData.classPK;

	useEffect(() => {
		if (collectionConfig.collection) {
			CollectionService.getCollectionField({
				activePage,
				classNameId: itemClassNameId,
				classPK: itemClassPK,
				collection: collectionConfig.collection,
				languageId,
				listItemStyle: collectionConfig.listItemStyle || null,
				listStyle: collectionConfig.listStyle,
				numberOfItems: collectionConfig.numberOfItems,
				numberOfItemsPerPage: collectionConfig.numberOfItemsPerPage,
				onNetworkStatus: dispatch,
				paginationType: collectionConfig.paginationType,
				templateKey: collectionConfig.templateKey || null,
			})
				.then((response) => {
					setCollection(
						response.length > 0 && response.items?.length > 0
							? response
							: {...response, ...DEFAULT_COLLECTION}
					);
				})
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
				});
		}
	}, [
		activePage,
		itemClassNameId,
		itemClassPK,
		collectionConfig.collection,
		collectionConfig.listItemStyle,
		collectionConfig.listStyle,
		collectionConfig.numberOfItems,
		collectionConfig.numberOfItemsPerPage,
		collectionConfig.paginationType,
		collectionConfig.templateKey,
		dispatch,
		languageId,
	]);

	return (
		<div className="page-editor__collection" ref={ref}>
			{!collectionIsMapped(collectionConfig) ? (
				<NotCollectionSelectedMessage />
			) : collection.content ? (
				<UnsafeHTML markup={collection.content} />
			) : (
				<Grid
					child={child}
					collection={collection.items}
					collectionConfig={collectionConfig}
					collectionId={item.itemId}
					collectionLength={collection.items.length}
					customCollectionSelectorURL={
						collection.customCollectionSelectorURL
					}
				/>
			)}

			{collectionConfig.paginationType && (
				<CollectionPagination
					collectionConfig={collectionConfig}
					collectionId={item.itemId}
					paginationType={collectionConfig.paginationType}
				/>
			)}
		</div>
	);
});

Collection.displayName = 'Collection';

export default Collection;
