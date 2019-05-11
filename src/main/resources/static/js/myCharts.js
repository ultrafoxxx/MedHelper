
var ctx = document.getElementById('myChart').getContext('2d');


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
            var chart = new Chart(ctx, {
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
        },
        error: function (e) {

            console.log("ERROR");

        }
    });
}

$("document").ready(function (event) {
    ajaxgetVisitStats();
});
