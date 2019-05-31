
var ctx = document.getElementById('myChart').getContext('2d');

var chart;


function ajaxgetVisitStats() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/adminPanel/lastWeekVisitStats",
        cache: true,
        timeout: 600000,
        success: function (data) {
            var labels = [];
            var myData = [];
            console.log(data);
            $.each(data, function (index, value) {
                labels.push(value.visitDate);
                myData.push(value.quantity);
            });

            if(chart == null) {
                chart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [{
                            label: 'Wizyty w ciągu poprzedniego tygodnia',
                            backgroundColor: 'rgb(255, 99, 132)',
                            borderColor: 'rgb(255, 99, 132)',
                            data: myData
                        }]
                    },
                    options: {}
                });
            }
            else {
                removeData(chart);
                addData(chart, labels, myData);
            }
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
}

function ajaxgetDoctorStats() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/adminPanel/doctorStats",
        cache: true,
        timeout: 600000,
        success: function (data) {
            var labels = [];
            var myData = [];
            console.log(data);
            $.each(data, function (index, value) {
                labels.push(value.name);
                myData.push(value.userId);
            });
            removeData(chart);
            addData(chart, labels, myData);
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
}

function ajaxgetDrugStats() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/adminPanel/drugStats",
        cache: true,
        timeout: 600000,
        success: function (data) {
            var labels = [];
            var myData = [];
            console.log(data);
            $.each(data, function (index, value) {
                labels.push(value.drugName);
                myData.push(value.drugCount);
            });
            removeData(chart);
            addData(chart, labels, myData);
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
}

function ajaxgetSpecialtyStats() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/adminPanel/specialtyStats",
        cache: true,
        timeout: 600000,
        success: function (data) {
            var labels = [];
            var myData = [];
            console.log(data);
            $.each(data, function (index, value) {
                labels.push(value.specialtyName);
                myData.push(value.specialtyCount);
            });
            removeData(chart);
            addData(chart, labels, myData);
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
}

$("document").ready(function (event) {
    ajaxgetVisitStats();
});

$("#chooseStat").on("change", function () {
    if($(this).val()==="1"){
        ajaxgetVisitStats();
    }
    else if($(this).val()==="2"){
        ajaxgetDoctorStats();
    }
    else if($(this).val()==="3"){
        ajaxgetDrugStats();
    }
    else {
        ajaxgetSpecialtyStats();
    }
});

function addData(charts, label, data) {
    for(var i=0;i<label.length;i++){
        charts.data.labels.push(label[i]);
    }

    charts.data.datasets.forEach(function (dataset){

        for(var i=0;i<data.length;i++){
            dataset.data.push(data[i]);
        }
    });
    charts.update();
}

function removeData(charts) {
    console.log("Hello");
    var len = charts.data.labels.length;
    for(var i=0;i<len;i++){
        charts.data.labels.pop();
    }
    charts.data.datasets.forEach(function (dataset) {
        for(var i=0;i<len;i++){
            dataset.data.pop();
        }
    });
    charts.update();
}
