<#ftl output_format="plainText">

<#import "report_var.ftl" as var>
<#assign delimiter = ";">
<#--------------------------------------------------------------------------->
<#compress>
    <@writeHeaders var.headers/>
    <@writeColums columns=csvRecords/>
</#compress>
<#--------------------------------------------------------------------------->
<#macro writeHeaders headers>
    <#list headers as header>${header} <#if header_has_next> ${delimiter} </#if></#list>
</#macro>
<#--------------------------------------------------------------------------->
<#macro writeColums columns>
    <#list columns as periodicity>
        ${periodicity.deviceId} ${delimiter} ${periodicity.date} ${delimiter} ${periodicity.lastTime} ${delimiter} ${periodicity.lastIndicator} ${delimiter} ${periodicity.average}
    </#list>
</#macro>