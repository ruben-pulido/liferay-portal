package com.liferay.style.book.css.variables.provider;

import com.liferay.frontend.css.variables.ScopedCSSVariables;
import com.liferay.frontend.css.variables.ScopedCSSVariablesProvider;
import com.liferay.portal.kernel.util.HashMapBuilder;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Component(service = ScopedCSSVariablesProvider.class)
public class StyleBookCSSVariablesProvider
	implements ScopedCSSVariablesProvider {

	@Override
	public Collection<ScopedCSSVariables> getScopedCSSVariablesCollection(
		HttpServletRequest httpServletRequest) {

		return Arrays.asList(
//			new ScopedCSSVariables() {
//				public String getScope() {
//					return ":root";
//				}
//
//				public Map<String, String> getCSSVariables() {
//					return HashMapBuilder.<String, String>put(
//						"foreground", "black"
//					).put(
//						"base-font", "\"Comic Sans\""
//					).build();
//				}
//			},
			new ScopedCSSVariables() {
				public String getScope() {
					return ".button";
				}

				public Map<String, String> getCSSVariables() {
					return HashMapBuilder.<String, String>put(
						"foreground", "green"
//					).put(
//						"background-color", "#f44336 !important"
					).build();
				}
			},
			new ScopedCSSVariables() {
				public String getScope() {
					return ".fragment_2";
				}

				public Map<String, String> getCSSVariables() {
					return HashMapBuilder.<String, String>put(
						"foreground", "green"
//					).put(
//						"ba	ckground-color", "#f44336 !important"
//					).put(
//						"primary", "#f44336 !important"
					).build();
				}
			},
			new ScopedCSSVariables() {
				public String getScope() {
					return ":root";
				}

				public Map<String, String> getCSSVariables() {
					return HashMapBuilder.<String, String>put(
						"foreground", "green"
//					).put(
//						"background-color", "#f44336 !important"
					).put(
						"primary", "#f44336 !important"
					).build();
				}
			}
		);

	}
}