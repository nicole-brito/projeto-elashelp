<!--    Mostra o total de tickets e também os que foram finalizados -->
fetch('/api/ticket/total-by-sector')
    .then(response => response.json())
    .then(totalData => {
        fetch('/api/ticket/finished-by-sector')
            .then(response => response.json())
            .then(finishedData => {
                var xValues = Object.keys(totalData); // Nomes dos setores
                var totalValues = Object.values(totalData); // Quantidade total de tickets
                var finishedValues = xValues.map(x => finishedData[x] || 0); // Quantidade de tickets finalizados

                new Chart("ticketsbysetorestatus", {
                    type: "bar",
                    data: {
                        labels: xValues,
                        datasets: [{
                            label: 'Total de Chamados',
                            data: totalValues,
                            backgroundColor: 'rgba(0, 123, 255, 0.5)',
                            borderColor: 'rgba(0, 123, 255, 1)',
                            borderWidth: 1
                        }, {
                            label: 'Chamados Finalizados',
                            data: finishedValues,
                            type: 'line',
                            fill: false,
                            borderColor: 'rgba(40, 167, 69, 1)'
                        }]
                    },
                    options: {
                        responsive: true,
                        title: {
                            display: true,
                            text: 'Total de Tickets e Tickets Finalizados por Setor'
                        },
                        tooltips: {
                            mode: 'index',
                            intersect: false
                        },
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                        }
                    }
                });
            });
    });

<!--    Mostra os tickets abertos ordenados por setor-->
fetch('/api/ticket/open-by-sector')
    .then(response => response.json())
    .then(data => {
        var xValues = Object.keys(data); // Nomes dos setores
        var yValues = Object.values(data); // Quantidade de tickets abertos

        var barColors = [
            "#d55a7c",
            "#00aba9",
            "#2b5797",
            "#e8c3b9",
            "#1e7145"
        ];

        new Chart("ticketsbysetor", {
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
                    text: "Tickets Abertos por Setor"
                }
            }
        });
    });

// Mostra os tickets por status
fetch('/api/ticket/by-status')
    .then(response => response.json())
    .then(data => {
        var labels = Object.keys(data); // Status
        var values = Object.values(data); // Quantidade de tickets

        var colors = [
            "#d55a7c",
            "#00aba9",
            "#2b5797",
            "#e8c3b9",
            "#1e7145"
        ];

        new Chart("ticketsByStatus", {
            type: "pie",
            data: {
                labels: labels,
                datasets: [{
                    backgroundColor: colors,
                    data: values
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'Tickets por Status'
                }
            }
        });
    });

fetch('api/ticket/by-priority')
    .then(response => response.json())
    .then(data => {
        var xValues = Object.keys(data); // Prioridades
        var yValues = Object.values(data); // Quantidade de tickets

        var barColors = [
            "#d55a7c",
            "#00aba9",
            "#2b5797",
            "#e8c3b9",
            "#1e7145"
        ];

        new Chart("ticketsByPriority", {
            type: "bar",
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
                    text: "Tickets por Prioridade"
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    });