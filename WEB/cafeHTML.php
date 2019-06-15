<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="logic.js"></script>
        <meta charset="windows-1251">
        <title>Оформление паспорта</title>
        <!--<meta name="viewport" content="width=device-width, initial-scale=1.0">-->
    </head>
    <body>
        <p align="right">
            Руководителю управления<br>

            развития предпринимательства,<br>

            потребительского рынка<br>

            и инновационной политики</p>

            <form action="cafeHTML.php" method="post">

        <p align="right">Ф.И.О. руководителя: <input type="text" size="40" name="names"></p>

        <h2 align="center">
            Заявление о выдаче паспорта размещения временной <br>
            организации быстрого обслуживания (летнего кафе)</h2>
        
        <p align="center">Наименование организации (Ф.И.О. руководителя юридического лица), Ф.И.О. индивидуального предпринимателя <input type="text" size="40" name="ind_names" ></p>
        
        <p align="center">ОГРН <input type="text" size="20" name="OGRN"> от <input type="date" size="20" name="OGRN_DATE"> <br><br>
        ИНН <input type="text" size="20" name = "INN"> от <input type="date" size="20" name = "INN_DATE">
        </p>
        
        <p style="line-height: 30px">Прошу выдать паспорт размещения временной организации быстрого<br>
            обслуживания (летнего кафе) 
            </p>

        <table>
        <tr><td>по адресу: <td><input type="text" size="15" name="adress_reg" placeholder="Регион"> <input type="text" size="15" name="adress_city"  placeholder="Город"> <input type="text" size="15" name="adress_street"  placeholder="Улица"> <input type="text" size="15" name="adress_home"  placeholder="Дом"> <input type="text" size="10" name="adress_kvart"  placeholder="Квартира"> </td> </tr>
       <tr> <td>на земельном участке, прилегающем к:</td> <td><input type="text" size="20" name="earsConnect" > </td> </tr>
       <tr> <td>кадастровый номер земельного участка (при наличии):</td>  <td><input type="text" size="20" name="kadstrNum" ></td>  </tr>
       <tr> <td>ОКАТО:</td>  <td><input type="text" size="20" name = "OKATO" ></td>  </tr>
        <tr><td>площадь объекта:</td>  <td><input type="number" size="20" name="Size" > </td> </tr>
       <tr> <td>специализация:</td>  <td><input type="text" size="20" name="specialization"></td>  </tr>
       <tr> <td>срок осуществления деятельности:</td>  <td><input type="date" size="20" name="date_after" ><input type="date" size="20" name="date_before"></td>  </tr>
       <tr> <td>контактный телефон:</td>  <td><input type="text" size="20" name="phone"> </td> </tr>
       <tr> <td>адрес (юридический и почтовый):</td>  <td><input type="text" size="15" name="mail_reg"  placeholder="Регион"><input type="text" size="15" name="mail_city"  placeholder="Город"><input type="text" size="15" name="mail_street" placeholder="Улица"><input type="text" size="15" name="mail_home"  placeholder="Дом"><input type="text" size="15" name="mail_kvart"  placeholder="Квартра"><input type="text" size="15" name="mail_index" placeholder="Индекс"> </td> </tr>
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
        <p align="center"><input type="submit" value="Отправить" onclick="return getData(this.form)"></p>
        </form>




        <?php 
        $connection = mysqli_connect('127.0.0.1', 'root', '', 'hakaton2');


        if($connection == false){
            echo "не удалось";
        }else{
            //echo "удалось";
        }

        $date_today = date("m.d.y");
       // $date_create = date_parse_from_format('Y-m-d', $date_today);
        $date_create = '2019-06-09';
        $date_end = '2019-06-16';//date('Y-m-d', strtotime($date_today. '+ 5 days') );

        $adress_reg = $_POST['adress_reg'];
        $adress_city = $_POST['adress_city'];
        $adress_street = $_POST['adress_street'];
        $specialization = $_POST['specialization'];

        $date_after_type = $_POST['date_after'];
        $date_after = '2019-06-09';
        $date_before_type = $_POST['date_before'];
       // $date_before = date_parse_from_format('Y-m-d', $date_before_type);
        $date_before = '2019-06-09';

        $adress_home = $_POST['adress_home'];
        $adress_kvart = $_POST['adress_kvart'];
        $earsConnect = $_POST['earsConnect'];
        $kadstrNum = $_POST['kadstrNum'];
        $OKATO = $_POST['OKATO'];
        $Size = $_POST['Size'];
        $names = $_POST['names'];
        $ind_names = $_POST['ind_names'];
        $mail_index = $_POST['mail_index'];
        $mail_reg = $_POST['mail_reg'];
        $mail_city = $_POST['mail_city'];
        $mail_street = $_POST['mail_street'];
        $mail_home = $_POST['mail_home'];
        $mail_kvart = $_POST['mail_kvart'];
        $OGRN = $_POST['OGRN'];
        $INN = $_POST['INN'];

        $date_type = $_POST['OGRN_DATE'];
        //$OGRN_DATE = date_parse_from_format('Y-m-d', $date_type);
        $OGRN_DATE = '2019-06-09';
        $date_type2 = $_POST['INN_DATE'];
       // $INN_DATE = date_parse_from_format('Y-m-d', $date_type2);
        $INN_DATE = '2019-06-09';

        $phone = $_POST['phone'];



        mysqli_query($connection, "insert into region (Region) values ('$adress_reg')" );
        mysqli_query($connection, "insert into region (Region) values ('$mail_reg')" );
        mysqli_query($connection, "insert into specialization (Spec) values ('$specialization')" );
        mysqli_query($connection, "insert into city (City, id_reg) values ('$adress_city', 
            (Select id_reg from Region where Region='$adress_reg'))" );
        mysqli_query($connection, "insert into city (City, id_reg) values ('$mail_city', 
            (Select id_reg from Region where Region='$mail_reg'))" );
        mysqli_query($connection, "insert into street (Street, pochta, id_city) values ('$adress_street', ' ', 
            (Select MIN(id_city) from City where City='$adress_city'))" );
        mysqli_query($connection, "insert into street (Street, pochta, id_city) values ('$mail_street', '$mail_index', 
            (Select MIN(id_city) from City where City='$mail_city'))" );

        mysqli_query($connection, "insert into statement (Org, OGRN, date_ogrn, INN, date_INN, contact, House, Apartment, id_adr)
         values ('$ind_names', '$OGRN', '2019-06-09', '$INN', '2019-06-09', '$phone', '$mail_home', '$mail_kvart', 
         (Select id_adr from street where Street='$mail_street'))" );

        mysqli_query($connection, "insert into general_cafe (FIO, time_first, time_last, date_create, date_end,
            House, Apartment, Object, Kadastr, OKATO, Square, id_adr, id_spec, id_st) 
            values ('$names', '$date_after', '$date_create', $date_end, '$adress_home', '$adress_kvart', '$earsConnect','$kadstrNum', 
           '$OKATO', $Size, (Select id_adr from Street where Street='$adress_street'),  
           (Select id_spec from Specialization where Spec='$specialization'),
           (Select id_st from Statement where Org='$ind_names' and OGRN='$OGRN' and INN='$INN')  )" );
       // $input = mysqli_query($connection, "insert into names (name) values ('$na') ");

        mysqli_close($connection);
        ?>

    </body>
</html>