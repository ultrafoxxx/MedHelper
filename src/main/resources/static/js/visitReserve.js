
var searchVisitsButton = $("#submitButton");

var doctorList = $("#doctorList");

var inputDate = $("#inputDate");

var inputPlace = $("#inputPlace");

var inputSpecialties = $("#inputSpecialties");




searchVisitsButton.click(function (event) {
    ajaxgetVisits(inputDate.val(), inputPlace.val(), inputSpecialties.val());
});


function ajaxgetVisits(dates, placeName, specialty) {

    var visits = new Object();
    visits.date = dates;
    visits.placeName = placeName;
    visits.specialty = specialty;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/reserveVisit/getVisitItems",
        cache: false,
        timeout: 600000,
        data: JSON.stringify(visits),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            doctorList.empty();
            $.each(data, function (index, value) {
                var mainDiv = $("<div class=\"shadow-sm p-3 mb-5 bg-white rounded\">");
                var myDesc = $("<div class=\"bg-success rounded myDesc\">");
                myDesc.text("ul. "+value.address+", "+value.city);
                mainDiv.append(myDesc);
                var myDoctor = $("<div class=\"myDoctor\">");
                var nameDiv = $("<div>");
                nameDiv.text(value.fullName);
                myDoctor.append(nameDiv);
                var detailButton = $("<button type=\"button\" class=\"btn btn-success detailsButton\">");
                detailButton.text('Szczegóły');
                myDoctor.append(detailButton);
                mainDiv.append(myDoctor);
                var myDesc = $("<div class=\"bg-success rounded myDesc\">");
                myDesc.text(value.specialty);
                mainDiv.append(myDesc);
                var hiddenInput = $("<input type='hidden' class='doctorId'>");
                hiddenInput.attr('value', value.doctorId);
                mainDiv.append(hiddenInput);
                var secondHiddenInput = $("<input type='hidden' class='placeId'>");
                secondHiddenInput.attr('value', value.placeId);
                mainDiv.append(secondHiddenInput);
                doctorList.append(mainDiv);
            });
            var detailsButtons = $(".detailsButton");
            detailsButtons.click(function (event) {
                console.log("Hello");
                ajaxgetTimes(inputDate.val(), this.parentElement.nextElementSibling.nextElementSibling
                    .nextElementSibling.getAttribute('value'), this.parentElement.nextElementSibling
                    .nextElementSibling.getAttribute('value'), this.parentElement.parentElement);
                $(this).attr('disabled', true);
            });
        }
    })
}


function ajaxgetTimes(dates, placeId, doctorId, element) {

    var visits = new Object();
    visits.date = dates;
    visits.placeId = placeId;
    visits.doctorId = doctorId;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/reserveVisit/getVisitTimes",
        cache: false,
        timeout: 600000,
        data: JSON.stringify(visits),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            var terms = $("<div id=\"terms\">");
            var description = $("<div class=\"shadow-sm p-3 bg-white rounded tableDesc\">");
            var numDesc = $("<div class=\"no-desc\">");
            numDesc.text("No.");
            description.append(numDesc);
            var timeDesc = $("<div class=\"time-desc\">");
            timeDesc.text("Time");
            description.append(timeDesc);
            var reserveDesc = $("<div class=\"reserve-desc\">");
            reserveDesc.text("Rezerwuj");
            description.append(reserveDesc);
            terms.append(description);
            $.each(data, function (index, value) {

                var mainDiv = $("<div class=\"shadow-sm p-3 bg-white rounded myTable\">");
                var number = $("<div class=\"no\">");
                number.text(index+1);
                mainDiv.append(number);
                var myTime = $("<div class=\"time\">");
                myTime.text(value.time);
                mainDiv.append(myTime);
                var myForm = $("<form action='http://localhost:8080/reserveVisit/confirm' method='get'>");
                var hiddenInput = $("<input type='hidden' class='visitIdInput' name='visitId'>");
                hiddenInput.attr('value', value.id);
                myForm.append(hiddenInput);
                var reserveButton = $('<button type="submit" class="btn btn-success reserveButton">');
                reserveButton.text('Rezerwuj');
                myForm.append(reserveButton);
                mainDiv.append(myForm);
                terms.append(mainDiv);
            });
            $(element).append(terms);
        }
    })
}
