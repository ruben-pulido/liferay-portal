/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {InputLocalized, ManagementToolbar} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import AsyncButton from '../common/AsyncButton';
import ManagementBar from '../common/BaseManagementBar';
import ERCInput from '../common/ERCInput';
import {Config, initializeConfig} from '../structure_builder/config';
import getRandomId from '../structure_builder/utils/getRandomId';

export default function PicklistBuilder({config}: {config: Config}) {
	initializeConfig(config);

	const [name, setName] = useState<Liferay.Language.LocalizedValue<string>>({
		[Liferay.ThemeDisplay.getDefaultLanguageId()]:
			Liferay.Language.get('untitled-structure'),
	});
	const [erc, setErc] = useState<string>(getRandomId());

	return (
		<div className="d-flex flex-column">
			<ManagementBar title={Liferay.Language.get('new-picklist')}>
				<ManagementToolbar.Item>
					<AsyncButton
						displayType="primary"
						label={Liferay.Language.get('save')}
						onClick={() => new Promise(() => null)}
					/>
				</ManagementToolbar.Item>
			</ManagementBar>

			<ClayLayout.ContainerFluid className="px-4" size="md" view>
				<ClayForm.Group
					className={classNames('ml-n3', {'has-error': !name})}
				>
					<InputLocalized
						aria-label={Liferay.Language.get('structure-label')}
						error={
							name
								? ''
								: Liferay.Language.get('this-field-is-required')
						}
						label={Liferay.Language.get('name')}
						onBlur={() => {
							setName(name);
						}}
						onChange={(name) => setName(name)}
						required
						translations={
							name as Liferay.Language.LocalizedValue<string>
						}
						validate
					/>

					<ERCInput onValueChange={setErc} value={erc} />

					<div className="panel-unstyled">
						<h3 className="panel-header panel-title text-secondary">
							{sub(
								Liferay.Language.get('x-options'),
								Liferay.Language.get('picklist')
							)}
						</h3>
					</div>
				</ClayForm.Group>
			</ClayLayout.ContainerFluid>
		</div>
	);
}
