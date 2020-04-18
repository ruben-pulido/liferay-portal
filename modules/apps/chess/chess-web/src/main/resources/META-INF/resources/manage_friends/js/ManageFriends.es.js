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

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {useTimeout} from 'frontend-js-react-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

/**
 * Handles actions to delete the friends for a player.
 */

const ManageFriends = ({
	actionUrl,
	friends,
	dialogId,
	portletNamespace,
}) => {
	const [currentFriends, setCurrentFriends] = useState(
		friends
	);
	const [loadingResponse, setLoadingResponse] = useState(false);

	const delay = useTimeout();

	const closeDialog = () => {
		const friendsDialog = Liferay.Util.getWindow(dialogId);

		if (friendsDialog && friendsDialog.hide) {
			friendsDialog.hide();
		}
	};

	const objectToPairArray = object => {
		const entries = Object.entries(object);
		const result = [];

		entries.forEach(([key, value]) => {
			result.push(`${key},${value}`);
		});

		return result;
	};

	const getFriend = friendId => {
		const friendIdNumber = Number(friendId);

		const friend = currentFriends.find(
			friend => friend.userId === friendIdNumber
		);

		return friend;
	};

	const handleDeleteFriendButtonClick = event => {
		const button = event.currentTarget;

		const collaboratorId = Number(button.dataset.collaboratorId);

		event.stopPropagation();

		setCurrentCollaborators(
			currentCollaborators.filter(
				collaborator => collaborator.userId != collaboratorId
			)
		);
	};

	const handleSaveButtonClick = () => {
		setLoadingResponse(true);

		const data = Liferay.Util.ns(portletNamespace, {
		});

		fetch(actionUrl, {
			body: objectToFormData(data),
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

	const setFriend = updatedFriend => {
		setCurrentFriends(
			currentFriends.map(friend => {
				if (friend.userId === updatedFriend.userId) {
					return {
						...friend,
						...updatedFriend,
					};
				}

				return friend;
			})
		);
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

		closeDialog();

		parentOpenToast(openToastParams);
	};

	const Friend = ({
		fullName,
		portraitURL,
		userId,
	}) => {
		return (
			<li
				className={classNames(
					'list-group-item',
					'list-group-item-action',
					'list-group-item-flex',
					{
						active: true,
					}
				)}
				data-friendid={userId}
				id={`friend${userId}`}
				role="button"
			>
				<div className="autofit-col">
				</div>
				<div className="autofit-col autofit-col-expand">
					<div className="autofit-row autofit-row-center">
						<div className="autofit-col autofit-col-expand">
							<strong>
								<span>{fullName}</span>
							</strong>
						</div>
						<div className="autofit-col">
							<ClayButtonWithIcon
								borderless
								data-friend-id={userId}
								disabled={loadingResponse}
								displayType="secondary"
								onClick={handleDeleteFriendButtonClick}
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
									<Friend
										{...friend}
										key={friend.userId}
									/>
								);
							})}
						</ul>
					</>
				) : (
					<div className="autofit-row autofit-row-center empty-friends">
						<div className="autofit-col autofit-col-expand">
							<div className="message-content">
								<h3>
									{Liferay.Language.get('no-friends')}
								</h3>
							</div>
						</div>
					</div>
				)}
			</div>
			<div className="modal-footer">
				<div className="modal-item-last">
					<ClayButton.Group spaced>
						<ClayButton
							disabled={loadingResponse}
							displayType="secondary"
							onClick={closeDialog}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							disabled={loadingResponse}
							displayType="primary"
							onClick={handleSaveButtonClick}
						>
							{loadingResponse && <ClayLoadingIndicator />}
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				</div>
			</div>
		</>
	);
};

ManageFriends.propTypes = {
	actionUrl: PropTypes.string.isRequired,
	friends: PropTypes.array.isRequired,
	dialogId: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string,
};

export default ManageFriends;
