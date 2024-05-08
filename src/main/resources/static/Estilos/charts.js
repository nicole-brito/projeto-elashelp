fetch('/api/tickets/open-by-sector')
    .then(response => response.json())
    .then(data => {
        var xValues = Object.keys(data); // Nomes dos setores
        var yValues = Object.values(data); // Quantidade de tickets abertos

        var barColors = [
            "#b91d47",
            "#00aba9",
            "#2b5797",
            "#e8c3b9",
            "#1e7145"
        ];

        new Chart("myChart", {
            type: "doughnut",
            data: {
                labels: xValues,
                datasets: [{
                    backgroundColor: barColors,
                    data: yValues
                }]
            },
            options: {
                title: {
                    display: true,
                    text: "Open Tickets by Sector"
                }
            }
        });
    });