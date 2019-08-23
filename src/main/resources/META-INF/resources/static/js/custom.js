var web_competitions_keyfigures = function (competitionPublicId){
	$.ajax({
		url : "/api/competitions/" + competitionPublicId + "/key-figures",
		method : "GET",
		success : function(data) {
			$("#totalRatings").prop('ratings',0).animate({
				 ratings: data.totalRatings
			    }, {
			        duration: 3000,
			        easing: 'swing',
			        step: function (now) {
			        	$("#totalRatings").text(Math.ceil(now));
			        }
			    });
	   	  
			$("#uniqueUser").prop('uniqueUser',0).animate({
				uniqueUser: data.uniqueUser
			    }, {
			        duration: 3000,
			        easing: 'swing',
			        step: function (now) {
			        	$("#uniqueUser").text(Math.ceil(now));
			        }
			    });
			
		},
		error : function(data) {
			console.log(data);
		}
	});
		
}

var web_competitions_statistic = function (competitionPublicId){
	$.ajax({
		url : "/api/competitions/" + competitionPublicId + "/statistic",
		method : "GET",
		success : function(data) {
			var title = [];
			var score = [];
			var backgroundColor = [];
			var bgColorPool = [
				"rgba(255, 99, 132, 0.2)",
				"rgba(255, 159, 64, 0.2)",
				"rgba(255, 205, 86, 0.2)",
				"rgba(75, 192, 192, 0.2)",
				"rgba(54, 162, 235, 0.2)",
				"rgba(153, 102, 255, 0.2)",
				"rgba(255, 99, 132, 0.2)",
				"rgba(255, 159, 64, 0.2)",
				"rgba(255, 205, 86, 0.2)",
				"rgba(75, 192, 192, 0.2)"
			]
			
			for (var i in data.scores) {
				if (i == 10) {
					break;
				}
				title.push(data.scores[i].title);
				score.push(data.scores[i].score);
				backgroundColor.push(bgColorPool[i]);
			}
	
			var chartdata = {
				labels : title,
				datasets : [ {
					label : 'Hall of Fame',
					data : score,
					backgroundColor: backgroundColor
				}]
			};
	
			var ctx = $("#mycanvas");
	
			var barGraph = new Chart(ctx, {
				type : 'bar',
				position : 'top',
				data : chartdata,
				
				options : {
					legend : {
						display : false
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
		},
		error : function(data) {
			console.log(data);
		}
	});

}