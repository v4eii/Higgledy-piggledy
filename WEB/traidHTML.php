<!DOCTYPE html>

<html>
    <head>
        
        <meta charset="utf-8">
        <title>Оформление паспорта</title>
        <!--<meta name="viewport" content="width=device-width, initial-scale=1.0">-->
    </head>
    <body>
        <p align="right">
            Руководителю управления<br>

            развития предпринимательства,<br>

            потребительского рынка<br>

            и инновационной политики</p>

        <p align="right">Ф.И.О. руководителя: <input type="text" size="40" id ="names"></p>

        <h2 align="center">
           Заявление о выдаче паспорта объекта мелкорозничной торговли<br>
           при проведении праздничных и иных массовых мероприятий,<br>
           имеющих временный характер<br></h2>
        
        <p align="center">Наименование организации (Ф.И.О. руководителя юридического лица), Ф.И.О. индивидуального предпринимателя</p>
        
        <p align="center">ОГРН <input type="text" size="20"> от <input type="text" size="20"> <br><br>
        ИНН <input type="text" size="20"> от <input type="text" size="20">
        </p>
        
        <p style="line-height: 30px">Прошу выдать паспорт объекта мелкорозничной торговли при проведении<br>

            праздничных и иных массовых мероприятий, имеющих временный характер, и<br>

            определить место для размещения</p>
        <table>
        <tr><td>тип объекта:</td><td><input type="text" size="20" id="object"> </td></tr>
        <tr><td>по адресу:</td><td><input type="text" size="20" id="adress"> </td></tr>
        <tr><td>специализация:</td><td><input type="text" size="20" id="specialization"></td> </tr>
        <tr><td>срок осуществления деятельности:</td><td><input type="text" size="20" id="date"> </td></tr>
        <tr><td>контактный телефон:</td><td><input type="text" size="20" id="phone"> </td></tr>
        <tr><td>адрес (юридический и почтовый):</td><td><input type="text" size="40" id="mail"> </td></tr>
        </table>
        
        <p style="line-height: 30px">В соответствии с требованиями Федерального закона <a href="http://docs.cntd.ru/document/901990046">от 27.07.2006<br>

N 152-ФЗ "О персональных данных"</a> даю согласие на сбор, систематизацию,<br>

накопление, хранение, уточнение (обновление, изменение), использование,<br>

распространение (в случаях, предусмотренных действующим законодательством<br>

Российской Федерации) предоставленных выше персональных данных. Настоящее<br>

согласие дано мною бессрочно.</p>
        
        <p align="right">Руководитель управления развития<br>
предпринимательства, потребительского<br>
рынка и инновационной политики<br>
Л.М.ПАРШИНА<br><br></p>
        
        
        <p><b>Загрузите керокопии документов:</b> <input type="file" name="photo" multiple accept="image/*,image/jpeg"></p>
        <p align="center"><button onclick="getData()">Отправить</button></p>

<script type="text/javascript">
            function getData(){
                alert("dsdfsfs");
            }
        </script>

    </body>
</html>