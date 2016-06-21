/**
 * 加载本月订单量曲线图
 * */
var randomScalingFactor = function(){ return Math.round(Math.random()*1000)};
function loadOrderLineChart() {
    var lineChartData = {
                         labels : ["January","February","March","April","May","June","July"],
                         datasets : [
                                     {
                                         label: "My First dataset",
                                         fillColor : "rgba(220,220,220,0.2)",
                                         strokeColor : "rgba(220,220,220,1)",
                                         pointColor : "rgba(220,220,220,1)",
                                         pointStrokeColor : "#fff",
                                         pointHighlightFill : "#fff",
                                         pointHighlightStroke : "rgba(220,220,220,1)",
                                         data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
                                     },
                                     {
                                         label: "My Second dataset",
                                         fillColor : "rgba(48, 164, 255, 0.2)",
                                         strokeColor : "rgba(48, 164, 255, 1)",
                                         pointColor : "rgba(48, 164, 255, 1)",
                                         pointStrokeColor : "#fff",
                                         pointHighlightFill : "#fff",
                                         pointHighlightStroke : "rgba(48, 164, 255, 1)",
                                         data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
                                     }
                                     ]
    }
    var dashboardLineChart = document.getElementById("dashboardLineChart").getContext("2d");
    window.myLine = new Chart(dashboardLineChart).Line(lineChartData, {
        responsive: true
    });
}

/**
 * 加载本月登陆量曲线图
 * */
function loadLoginsLineChart() {

}

/**
 * 加载本月新用户数曲线图
 * */
function loadNewPlayerLineChart() {

}

/**
 * 加载本月收入曲线图
 * */
function loadRevenueLineChart() {

}

/**
 * 加载玩家/游戏占比图
 * */
function loadPlayerGamePieChart() {
    var pieData = [
                   {
                       value: 300,
                       color:"#30a5ff",
                       highlight: "#62b9fb",
                       label: "Blue"
                   },
                   {
                       value: 50,
                       color: "#ffb53e",
                       highlight: "#fac878",
                       label: "Orange"
                   },
                   {
                       value: 100,
                       color: "#1ebfae",
                       highlight: "#3cdfce",
                       label: "Teal"
                   },
                   {
                       value: 120,
                       color: "#f9243f",
                       highlight: "#f6495f",
                       label: "Red"
                   }

                   ];
    var playerGamePie = document.getElementById("playerGamePie").getContext("2d");
    window.myPie = new Chart(playerGamePie).Pie(pieData, {responsive : true});
}

/**
 * 加载玩家/分销商占比图
 * */
function loadPlayerDistributorPieChart() {
    var pieData = [
                   {
                       value: 300,
                       color:"#30a5ff",
                       highlight: "#62b9fb",
                       label: "Blue"
                   },
                   {
                       value: 50,
                       color: "#ffb53e",
                       highlight: "#fac878",
                       label: "Orange"
                   },
                   {
                       value: 100,
                       color: "#1ebfae",
                       highlight: "#3cdfce",
                       label: "Teal"
                   },
                   {
                       value: 120,
                       color: "#f9243f",
                       highlight: "#f6495f",
                       label: "Red"
                   }

                   ];
    var playerDistributorPie = document.getElementById("playerDistributorPie").getContext("2d");
    window.myPie = new Chart(playerDistributorPie).Pie(pieData, {responsive : true});
}

/**
 * 加载收入/游戏占比图
 * */
function loadRevenueGamePieChart() {
    var pieData = [
                   {
                       value: 300,
                       color:"#30a5ff",
                       highlight: "#62b9fb",
                       label: "Blue"
                   },
                   {
                       value: 50,
                       color: "#ffb53e",
                       highlight: "#fac878",
                       label: "Orange"
                   },
                   {
                       value: 100,
                       color: "#1ebfae",
                       highlight: "#3cdfce",
                       label: "Teal"
                   },
                   {
                       value: 120,
                       color: "#f9243f",
                       highlight: "#f6495f",
                       label: "Red"
                   }

                   ];
    var revenueGamePie = document.getElementById("revenueGamePie").getContext("2d");
    window.myPie = new Chart(revenueGamePie).Pie(pieData, {responsive : true});
}

/**
 * 加载收入/分销商占比图
 * */
function loadRevenueDistributorPieChart() {
    var pieData = [
                   {
                       value: 300,
                       color:"#30a5ff",
                       highlight: "#62b9fb",
                       label: "Blue"
                   },
                   {
                       value: 50,
                       color: "#ffb53e",
                       highlight: "#fac878",
                       label: "Orange"
                   },
                   {
                       value: 100,
                       color: "#1ebfae",
                       highlight: "#3cdfce",
                       label: "Teal"
                   },
                   {
                       value: 120,
                       color: "#f9243f",
                       highlight: "#f6495f",
                       label: "Red"
                   }

                   ];
    var revenueDistributorPie = document.getElementById("revenueDistributorPie").getContext("2d");
    window.myPie = new Chart(revenueDistributorPie).Pie(pieData, {responsive : true});
}