function getData(data){

if(data.names != null && data.names.value.length < 3  ){
	alert('Неверно заполнено поле "Ваше имя"');
	return false;
}

if(data.adress != null && data.adress.value.length < 5){
	alert('Неверно заполнено поле "по адресу"');
	return false;
}

if(data.earsConnect != null && data.earsConnect.value.length < 5){
	alert('Неверно заполнено поле "на земельном участке, прилегающем к"');
	return false;
}

if(data.kadstrNum != null && data.kadstrNum.value.length != 13 && !(/^[0-9]+z/.test(data.phone.value+"z"))){
alert('Неверно заполнено поле "кадастровый номер земельного участка"');
	return false;
}

if(data.OKATO != nill && !preg_match("/^[\d]+$/", data.OKATO.value)){
	alert('Неверно заполнено поле "ОКАТО"');
	return false;
}

if(data.Size != nill && !preg_match("/^[\d]+$/", data.Size.value)){
	alert('Неверно заполнено поле "площадь объекта"');
	return false;
}

if(data.specialization != null && data.specialization.value.length < 5){
	alert('Неверно заполнено поле "специализация"');
	return false;
}

if(data.date != null && data.date.value.length == 0){
	alert('Неверно заполнено поле "срок осуществления деятельности"');
	return false;
}

if(data.phone != null && data.phone.value.length != 8 && !preg_match("/^[\d]+$/", data.Size.value)){
	alert('Неверно заполнено поле "контактный телефон"');
	return false;
}

if(data.mail != null && data.mail.value.length < 6 && !(/^w+[-_.]*w+@w+-?w+.[a-z]{2,4}$/.test(data.email.value))){
	alert('Неверно заполнено поле "адрес (юридический и почтовый)"');
	return false;
}

}