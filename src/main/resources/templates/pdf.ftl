
<#import "report_var.ftl" as var>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"></meta>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
        }

        table, th, td {
            border: 1px solid black;
        }

        td {
            white-space: nowrap;
        }

        .columnId {
            white-space: normal;
        }

        .img {
            display: block;
            width: 300px;
            height: 300px;
            object-fit: contain;
            margin: 20px auto;
        }

    </style>
    <title>Отчет по счетчику ${meterId}</title>
</head>
<body>
<#if isExist!false>
    <h2>Отчет по счетчику ${meterId} <#if dateFrom??> за период ${dateFrom} - ${dateTo}</#if></h2>
    <table>
        <tr>
            <#list var.headers as header>
                <th>
                    ${header}
                </th>
            </#list>
        </tr>
        <#list records as periodicity>
            <tr>
                <td class="columnId">${periodicity.deviceId}</td>
                <td>${periodicity.date}</td>
                <td>${periodicity.lastTime}</td>
                <td>${periodicity.lastIndicator}</td>
                <td>${periodicity.average}</td>
            </tr>
        </#list>
    </table>
    <div class="wrapper">
        <img class="img" src="data:image/png;base64, ${imagePath}" alt="smile"></img>
    </div>
<#else>
    <h2>Не найдена информация по счетчику</h2>
</#if>
</body>
</html>
