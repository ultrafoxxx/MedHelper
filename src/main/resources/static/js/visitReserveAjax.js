
var placeHints = $('#hints2');

var inputPlace = $('#inputPlace');

var inputRooms = $('#inputRooms');

var tableBody = $('#tableBody');

var submitButton = $('#submitButton');

var inputDate = $('#inputDate');

var hiddenDoctor = $('#doctorId');

var inputTime = $('#inputTime');

var reserveButton = $('#reserveButton');

var invalidReserve = $('#invalidReserve');

var invalidData = $('#invalidData');

var chosenTimes = [];



placeHints.hide();

inputPlace.keyup(function (event) {
    ajaxgetPlaces();

});


submitButton.click(function (event) {
    if(inputPlace.val() === '' || inputRooms.val() === '' || inputDate.val() === '' || inputTime.val() === ''
    || parseInt(inputTime.val())<=0){
        invalidData.attr('style', 'display: initial;');
    }
    else {
        ajaxgetVisits($(inputDate).val(), $(inputRooms).val(), $(hiddenDoctor).val(), $(inputTime).val());
    }

});

reserveButton.click(function () {
    if(chosenTimes.length === 0){
        invalidReserve.attr('style', 'display: initial;');
    }
    else {
        ajaxreserveVisits($(inputDate).val(), $(inputRooms).val(), $(hiddenDoctor).val(), $(inputTime).val());
    }

});

function ajaxgetPlaces() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/adminPanel/getHints?data="+$(inputPlace).val(),
        cache: false,
        timeout: 600000,
        success: function (data) {
            placeHints.empty();
            $.each(data, function (index, value) {
                newListElement = $("<button type=\"button\" class=\"places list-group-item list-group-item-action list-group-item-success\">");
                newListElement.text(value.fullName);
                newListElement.attr('id', value.id);
                placeHints.append(newListElement);
            });
            if ($(inputPlace).val() === "") {

                $(placeHints).hide();
            } else {
                $(placeHints).show();
            }


            $("button.places").click(function (event) {
                $(inputPlace).val($(this).text());
                $(placeHints).hide();
                $(inputRooms).removeAttr('disabled');
                $(inputRooms).empty();
                ajaxgetRooms($(this).attr('id'));
            });
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
}


function ajaxgetRooms(placeId) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/adminPanel/getRooms?placeId="+placeId,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $.each(data, function (index, value) {
                newListElement = $("<option>");
                newListElement.text(value.nrSali);
                newListElement.attr('value', value.id);
                inputRooms.append(newListElement);
            });
        }
    })
}

function ajaxgetVisits(dates, gabinetId, doctorId, visitTime) {

    var visits = new Object();
    visits.date = dates;
    visits.gabinetId = gabinetId;
    visits.doctorId = doctorId;
    visits.visitTime = visitTime;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/adminPanel/getVisitsInfo",
        cache: false,
        timeout: 600000,
        data: JSON.stringify(visits),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            tableBody.empty();
            $.each(data, function (index, value) {
                inputRooms.append(newListElement);
                var newRow = $("<tr>");
                var newTD = $("<td>");
                newTD.text(value.time);
                newRow.append(newTD);
                var nextTD = $("<td>");
                var myTime = value.time.toString().split(":");
                var endTimeMinutes = parseInt(myTime[0])*60+parseInt(myTime[1])+parseInt(value.durationTime);
                nextTD.text(minutesToString(endTimeMinutes));
                newRow.append(nextTD);
                var newListElement = $("<button type=\"button\" class=\"btn btn-success reserve-button\">");
                newListElement.text('Dodaj');
                var buttonTD = $("<td>");
                buttonTD.append(newListElement);
                newRow.append(buttonTD);
                tableBody.append(newRow);
            });
            var reserveButtons = $('.reserve-button');
            reserveButtons.click(function (event) {
                if(this.textContent === 'Dodaj'){
                    var chosenTimeElement = this.parentNode.parentNode;
                    this.textContent = 'Odznacz';
                    chosenTimeElement.setAttribute('style', 'background-color: #75a0e5;');
                    chosenTimes.push(chosenTimeElement.childNodes.item(0).textContent);
                    console.log(chosenTimes);
                    invalidReserve.attr('style', 'display: none;');
                }
                else {
                    var chosenTimeElement = this.parentNode.parentNode;
                    this.textContent = 'Dodaj';
                    chosenTimeElement.removeAttribute('style');
                    chosenTimes = jQuery.grep(chosenTimes, function (value) {
                        return value !== chosenTimeElement.childNodes.item(0).textContent;
                    });
                    console.log(chosenTimes);
                }
            });
        }
    })
}

function ajaxreserveVisits(dates, gabinetId, doctorId, visitTime) {

    var visits = new Object();
    visits.date = dates;
    visits.gabinetId = gabinetId;
    visits.doctorId = doctorId;
    visits.visitTime = visitTime;
    visits.time = chosenTimes;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/adminPanel/sendVisitData",
        cache: false,
        timeout: 600000,
        data: JSON.stringify(visits),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            window.location = "http://localhost:8080/adminPanel";
        }
    })
}

function minutesToString(minutes){
    var time = "";
    var hours = Math.floor(minutes / 60).toString();
    time += hours+':';
    if(hours<10){
        time = "0" + time;
    }
    var mins = (minutes % 60).toString();
    if(mins < 10){
        mins = "0"+mins;
    }
    time += mins + ":00";
    return time;
}
