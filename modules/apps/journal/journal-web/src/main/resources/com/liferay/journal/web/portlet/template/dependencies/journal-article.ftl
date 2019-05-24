<#include "init.ftl">

<#assign
	variableData = name + ".getData()?eval"
	variableFriendlyUrl = name + ".getFriendlyUrl()"
/>

<#if repeatable>
	<#assign
		variableData = "cur_" + variableData
		variableFriendlyUrl = "cur_" + variableFriendlyUrl
	/>
</#if>

<a href="${getVariableReferenceCode(variableFriendlyUrl)}">
	${getVariableReferenceCode(variableData).title}
</a>