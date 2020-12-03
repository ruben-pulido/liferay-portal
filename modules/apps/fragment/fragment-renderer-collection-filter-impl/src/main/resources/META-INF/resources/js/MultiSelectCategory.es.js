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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React, {useState} from 'react';

const MultiSelectCategory = () => {
  const items = [
	{
	  checked: false,
	  label: "Practice",
	  onChange: () => {
	  	alert("Practice changed");
	  },
	  type: "checkbox",
	  value: "39128"
	},
	{
	  checked: false,
	  label: "Competition",
	  onChange: () => {
	  	alert("Competition changed");
	  },
	  type: "checkbox",
	  value: "39131"
	}
  ];
  const [categories, setCategories] = useState({"39128": false, "39131": false});

  return (
    <ClayDropDownWithItems
      footerContent={
        <>
          <ClayButton>{"Apply"}</ClayButton>
        </>
      }
      items={items}
      trigger={<ClayButton>{"Select"}</ClayButton>}
    />
  );
};

export default () => (
	<MultiSelectCategory />
);
