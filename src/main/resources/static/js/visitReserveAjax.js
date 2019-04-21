
var placeHints = $('#hints2');

var inputPlace = $('#inputPlace');

var inputRooms = $('#inputRooms');

var submitButton = $('#submitButton');

var inputDate = $('#inputDate');

placeHints.hide();

inputPlace.keyup(function (event) {
    ajaxgetPlaces();

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
