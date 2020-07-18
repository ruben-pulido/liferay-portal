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
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

/**
 * Handles actions to manage the friends for a player.
 */

const ManageFriends = ({actionUrl, friends, portletNamespace}) => {
	const [currentFriends, setCurrentFriends] = useState(friends);
	const [loadingResponse, setLoadingResponse] = useState(false);

	const getFriend = friendId => {
		const friendIdNumber = Number(friendId);

		const friend = currentFriends.find(
			friend => friend.userId === friendIdNumber
		);

		return friend;
	};

	const handleAddFavoriteFriendButtonClick = event => {
		const button = event.currentTarget;

		const friendId = Number(button.dataset.friendId);

		alert('friendId: ' + friendId);

		const friend = getFriend(friendId);

		friend.isFavorite = true;

		const data = Liferay.Util.ns(portletNamespace, {
			friendId,
		});

		const bodyValue = objectToFormData(data);

		fetch(actionUrl, {
			body: bodyValue,
			// 			body: JSON.stringify(data),
			method: 'POST',
		})
			.then(response => {
				const jsonResponse = response.json();

				return response.ok
					? jsonResponse
					: jsonResponse.then(json => {
							const error = new Error(
								json.errorMessage || response.statusText
							);
							throw Object.assign(error, {response});
					  });
			})
			.then(json => {
				showNotification(json.successMessage);

				setLoadingResponse(false);
			})
			.catch(error => {
				showNotification(error.message, true);

				setLoadingResponse(false);
			});
	};

	const showNotification = (message, error) => {
		const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

		const openToastParams = {
			message,
		};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		parentOpenToast(openToastParams);
	};

	const Friend = ({fullName, isFavorite, userId}) => {
		return (
			<li data-friendid={userId} id={`friend${userId}`} role="button">
				<div className="autofit-col"></div>
				<div className="autofit-col autofit-col-expand">
					<div className="autofit-row autofit-row-center">
						<div className="autofit-col autofit-col-expand">
							<strong>
								<span>{fullName + '' + isFavorite}</span>
							</strong>
						</div>
						<div className="autofit-col">
							<span>{isFavorite}</span>
						</div>
						<div className="autofit-col">
							<ClayButtonWithIcon
								borderless
								data-friend-id={userId}
								disabled={loadingResponse}
								displayType="secondary"
								onClick={handleAddFavoriteFriendButtonClick}
								symbol="times-circle"
							/>
						</div>
					</div>
				</div>
			</li>
		);
	};

	return (
		<>
			<div className="inline-scroller modal-body">
				{currentFriends.length ? (
					<>
						<ul className="list-group">
							{currentFriends.map(friend => {
								return (
									<Friend {...friend} key={friend.userId} />
								);
							})}
						</ul>
					</>
				) : (
					<div className="autofit-row autofit-row-center empty-friends">
						<div className="autofit-col autofit-col-expand">
							<div className="message-content">
								<h3>{Liferay.Language.get('no-friends')}</h3>
							</div>
						</div>
					</div>
				)}
			</div>
		</>
	);
};

ManageFriends.propTypes = {
	actionUrl: PropTypes.string.isRequired,
	friends: PropTypes.array.isRequired,
	portletNamespace: PropTypes.string,
};

export default ManageFriends;
