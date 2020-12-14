"use strict";

$(function () {
    setTime();
    setInterval(setTime, 9000);
});

function setTime() {
    let date = new Date();
    let hour = (date.getHours() < 10 ? '0' : '') + date.getHours();
    let minutes = (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
    let seconds = (date.getSeconds() < 10 ? '0' : '') + date.getSeconds();
    $('.clock').html(hour + ':' + minutes + ':' + seconds);
    let day = (date.getDate() < 10 ? '0' : '') + date.getDate();
    let month = (date.getMonth() < 10) ? '0' + date.getMonth() + '/' : date.getMonth() + 1 + '';
    let year = date.getFullYear();
    $('.day').html(day + '-' + month + '-' + year);
}