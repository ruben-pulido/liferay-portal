<#-- Common -->

<#if repeatable>
	<#assign name = "cur_" + stringUtil.replace(name, ".", "_") />
</#if>

<#assign variableName = name + ".getData()" />

<#-- Util -->

RUBEN 2

<#function getVariableReferenceCode variableName>
		<#return "${" + variableName + "}">
</#function>