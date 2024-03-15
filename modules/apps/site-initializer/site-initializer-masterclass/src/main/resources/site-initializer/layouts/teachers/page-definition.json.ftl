{
	"pageElement": {
		"id": "acd0c3ba-85f0-6667-efa8-bfd1e081bd63",
		"pageElements": [
			{
				"definition": {
					"indexed": true,
					"layout": {
						"widthType": "Fixed"
					}
				},
				"id": "57ca7629-f412-c3d3-d316-76b5f4ded1d4",
				"pageElements": [
					{
						"definition": {
							"fragment": {
								"key": "masterclass-heading",
								"siteKey": "s1"
							},
							"fragmentConfig": {
								"displayLevel": "0",
								"headingLevel": "h5"
							},
							"fragmentFields": [
								{
									"id": "element-text",
									"value": {
										"fragmentLink": {},
										"text": {
											"value_i18n": {
												"en_US": "Teachers"
											}
										}
									}
								}
							],
							"fragmentStyle": {
								"fontFamily": "fontFamilySansSerif",
								"fontWeight": "fontWeightBold",
								"marginBottom": "9",
								"textAlign": "center"
							},
							"fragmentViewports": [
								{
									"fragmentViewportStyle": {
										"marginBottom": "5"
									},
									"id": "tablet"
								}
							],
							"indexed": true
						},
						"id": "9ed12f29-1b2b-f051-ce44-193bf469864e",
						"type": "Fragment"
					}
				],
				"type": "Section"
			},
			[#list 1..siteInitializerConfiguration.layouts.teachers.numberOfFragments as fragmentIndex]
			{
				"definition": {
					"fragmentStyle": {
						"marginBottom": "10"
					},
					"fragmentViewports": [
						{
							"fragmentViewportStyle": {
								"marginBottom": "5"
							},
							"id": "tablet"
						}
					],
					"indexed": true,
					"layout": {
						"widthType": "Fixed"
					}
				},
				"id": "a3207415-377e-67c6-1bce-12ac102746a5-${fragmentIndex}",
				"pageElements": [
					{
						"definition": {
							"fragment": {
								"key": "masterclass-absolute-container",
								"siteKey": "s1"
							},
							"fragmentConfig": {
								"positionTop": -90,
								"positionRight": 0,
								"positionLeft": -100,
								"positionBottom": 0,
								"position": "absolute",
								"zIndex": 0
							},
							"fragmentFields": [],
							"fragmentViewports": [
								{
									"fragmentViewportStyle": {
										"hidden": true
									},
									"id": "tablet"
								}
							],
							"indexed": true
						},
						"id": "2248dd8c-9513-cfde-a896-d9b63b251f27-${fragmentIndex}",
						"pageElements": [
							{
								"definition": {},
								"id": "6bb7987c-e446-07db-209f-b4c02bd7c639-${fragmentIndex}",
								"pageElements": [
									{
										"definition": {
											"fragmentStyle": {
												"backgroundColor": "brandColor1",
												"height": "320px",
												"width": "320px"
											},
											"indexed": true,
											"layout": {}
										},
										"id": "db55e113-a15e-96f3-5e18-7742f1f30dcd-${fragmentIndex}",
										"type": "Section"
									}
								],
								"type": "FragmentDropZone"
							}
						],
						"type": "Fragment"
					},
					{
						"definition": {
							"gutters": true,
							"indexed": true,
							"modulesPerRow": 2,
							"numberOfColumns": 2,
							"reverseOrder": false,
							"rowViewports": [
								{
									"id": "landscapeMobile",
									"rowViewportDefinition": {}
								},
								{
									"id": "portraitMobile",
									"rowViewportDefinition": {}
								},
								{
									"id": "tablet",
									"rowViewportDefinition": {
										"modulesPerRow": 1
									}
								}
							],
							"verticalAlignment": "middle"
						},
						"id": "df0b81ca-f4d0-a003-08ca-0c04b1604d53-${fragmentIndex}",
						"pageElements": [
							{
								"definition": {
									"columnViewports": [
										{
											"columnViewportDefinition": {},
											"id": "landscapeMobile"
										},
										{
											"columnViewportDefinition": {},
											"id": "portraitMobile"
										},
										{
											"columnViewportDefinition": {
												"size": 12
											},
											"id": "tablet"
										}
									],
									"size": 7
								},
								"id": "357c0126-cca7-a2e5-d22a-48f339c9a5de-${fragmentIndex}",
								"pageElements": [
									{
										"definition": {
											"fragment": {
												"key": "masterclass-image",
												"siteKey": "s1"
											},
											"fragmentConfig": {
												"imageSize": "w-100"
											},
											"fragmentFields": [
												{
													"id": "image-square",
													"value": {
														"fragmentImage": {
															"url": {
																"mapping": {
																	"fieldKey": "DDMStructure_Image",
																	"itemReference": {
																		"className": "com.liferay.journal.model.JournalArticle",
																		"classPK": [$JOURNAL_ARTICLE_ASSET_ENTRY_CLASS_PK:TEACHER-${fragmentIndex}$]
																	}
																}
															}
														},
														"fragmentLink": {}
													}
												}
											],
											"fragmentStyle": {
												"marginRight": "8"
											},
											"fragmentViewports": [
												{
													"fragmentViewportStyle": {
														"marginBottom": "4"
													},
													"id": "tablet"
												}
											],
											"indexed": true
										},
										"id": "e4bfd1bf-43b5-8cb0-84e4-74cab0dfd3d4-${fragmentIndex}",
										"type": "Fragment"
									}
								],
								"type": "Column"
							},
							{
								"definition": {
									"columnViewports": [
										{
											"columnViewportDefinition": {},
											"id": "landscapeMobile"
										},
										{
											"columnViewportDefinition": {},
											"id": "portraitMobile"
										},
										{
											"columnViewportDefinition": {
												"size": 12
											},
											"id": "tablet"
										}
									],
									"size": 5
								},
								"id": "52d3926c-1d30-db05-9b05-7750d3c25df2-${fragmentIndex}",
								"pageElements": [
									{
										"definition": {
											"fragmentStyle": {
												"marginRight": "5"
											},
											"indexed": true,
											"layout": {
												"marginRight": 4
											}
										},
										"id": "38427f71-74d0-72d4-601f-bbaffede4770-${fragmentIndex}",
										"pageElements": [
											{
												"definition": {
													"fragment": {
														"key": "masterclass-text-block",
														"siteKey": "s1"
													},
													"fragmentConfig": {},
													"fragmentFields": [
														{
															"id": "element-text",
															"value": {
																"fragmentLink": {},
																"text": {
																	"value_i18n": {
																		"en_US": "${fragmentIndex}."
																	}
																}
															}
														}
													],
													"fragmentStyle": {
														"fontFamily": "fontFamilySansSerif",
														"fontSize": "fontSizeLg",
														"marginBottom": "2",
														"textColor": "gray600Color"
													},
													"indexed": true
												},
												"id": "974b2168-a9d6-31b1-42e6-efcb72b2119f-${fragmentIndex}",
												"type": "Fragment"
											},
											{
												"definition": {
													"fragment": {
														"key": "masterclass-heading",
														"siteKey": "s1"
													},
													"fragmentConfig": {
														"displayLevel": "0",
														"headingLevel": "h1"
													},
													"fragmentFields": [
														{
															"id": "element-text",
															"value": {
																"fragmentLink": {},
																"text": {
																	"mapping": {
																		"fieldKey": "DDMStructure_Name",
																		"itemReference": {
																			"className": "com.liferay.journal.model.JournalArticle",
																			"classPK": [$JOURNAL_ARTICLE_ASSET_ENTRY_CLASS_PK:TEACHER-${fragmentIndex}$]
																		}
																	}
																}
															}
														}
													],
													"fragmentStyle": {
														"marginBottom": "2"
													},
													"indexed": true
												},
												"id": "395bb771-c1cf-6514-a904-af1fa46070b8-${fragmentIndex}",
												"type": "Fragment"
											},
											{
												"definition": {
													"fragment": {
														"key": "masterclass-text-block",
														"siteKey": "s1"
													},
													"fragmentConfig": {},
													"fragmentFields": [
														{
															"id": "element-text",
															"value": {
																"fragmentLink": {},
																"text": {
																	"mapping": {
																		"fieldKey": "DDMStructure_Info",
																		"itemReference": {
																			"className": "com.liferay.journal.model.JournalArticle",
																			"classPK": [$JOURNAL_ARTICLE_ASSET_ENTRY_CLASS_PK:TEACHER-${fragmentIndex}$]
																		}
																	}
																}
															}
														}
													],
													"fragmentStyle": {
														"fontSize": "fontSizeLg",
														"marginBottom": "4",
														"textColor": "secondaryColor"
													},
													"fragmentViewports": [
														{
															"fragmentViewportStyle": {
																"marginBottom": "3"
															},
															"id": "tablet"
														}
													],
													"indexed": true
												},
												"id": "fc344976-cb2d-236f-64da-30a8874e4f78-${fragmentIndex}",
												"type": "Fragment"
											}
										],
										"type": "Section"
									}
								],
								"type": "Column"
							}
						],
						"type": "Row"
					}
				],
				"type": "Section"
			}
			[#sep],[/#sep]
			[/#list]
		],
		"type": "Root"
	},
	"settings": {
		"colorSchemeName": "01",
		"masterPage": {
			"key": "main-2"
		},
		"themeName": "Classic"
	},
	"version": 1.1
}