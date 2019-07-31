package com.liferay.journal.web.internal.info.renderer;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component(service = InfoItemRenderer.class)
public class JournalInfoRenderer implements InfoItemRenderer<JournalArticle> {

	@Override
	public void render(
		JournalArticle journalArticle, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		httpServletRequest.setAttribute(
			WebKeys.ASSET_RENDERER_FACTORY, assetRendererFactory);

		try {
			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					journalArticle.getResourcePrimKey());

			assetRenderer.include(
				httpServletRequest, httpServletResponse,
				AssetRenderer.TEMPLATE_FULL_CONTENT);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
