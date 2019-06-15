function getData(data){
  if(data.names != null && data.names.value.length < 3  ){
  	alert('Неверно заполнено поле "Ваше имя"');
  	return false;
  }

  if(data.object != null){
    alert('Неверно заполнено поле "тип объекта"');
    return false;
  }

  if(data.adress != null && data.adress.value.length < 5){
  	alert('Неверно заполнено поле "по адресу"');
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
  if(data.phone != null && data.phone.value.length != 8){
  	alert('Неверно заполнено поле "контактный телефон"');
  	return false;
  }
  if(data.mail != null && data.mail.value.length < 6)){
  	alert('Неверно заполнено поле "адрес (юридический и почтовый)"');
  	return false;
  }
}
