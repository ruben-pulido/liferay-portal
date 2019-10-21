package com.liferay.layout.page.template.internal.upgrade.v3_1_1;

import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class UpgradePreviewFileEntryId extends UpgradeProcess {
	public UpgradePreviewFileEntryId(
		DLFileEntryLocalService dlFileEntryLocalService) {
	}

	@Override
	protected void doUpgrade() throws Exception {

		// Verificar si existe el repository asociado al nuevo portlet en la tabla de repository
		// Si no E añadir una entrada a la tabla
		// (por groupId)

		// Query to get all previewFileEntryIds > 0 de LayoutPageTemplateEntry

		// Update del repositoryId asociado a ese previewFileEntryId
		

	}

	protected void upgradeDLFileEntryRepositoryId() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select layoutPageTemplateStructureId, groupId, companyId, ");
		sb.append("userId, userName, createDate, data_ from ");
		sb.append("LayoutPageTemplateStructure");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long layoutPageTemplateStructureId = rs.getLong(
						"layoutPageTemplateStructureId");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");
					String data = rs.getString("data_");

					_updateLayoutPageTemplateStructureRels(
						groupId, companyId, userId, userName, createDate,
						layoutPageTemplateStructureId, data);
				}
			}
		}
	}

}
