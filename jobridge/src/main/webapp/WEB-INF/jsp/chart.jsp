<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <title>気分のグラフ</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <h1>利用者 ${userId} の気分のグラフ</h1>

    <!-- グラフ表示領域 -->
    <canvas id="moodChart" width="300" height="150"></canvas>

	<script>
	    const moodData = ${moodDataJson};
	    console.log("Mood Data: ", moodData);
	
	    if (!moodData || moodData.length === 0) {
	        alert("選択した期間にはデータがありません。");
	    } else {
	        const labels = moodData.map(data => data.date);
	        const dataPoints = moodData.map(data => data.mood);
	
	        const ctx = document.getElementById('moodChart').getContext('2d');
	        new Chart(ctx, {
	            type: 'line',
	            data: {
	                labels: labels,
	                datasets: [{
	                    label: '気分の推移',
	                    data: dataPoints,
	                    borderColor: '#FF5733', // ラインの色
	                    backgroundColor: 'rgba(255, 87, 51, 0.2)', // 塗りつぶしの色
	                    borderWidth: 4, // ラインの幅
	                    tension: 0.4 // ラインを滑らかに
	                }]
	            },
	            options: {
	                plugins: {
	                    title: {
	                        display: true,
	                        text: '気分の変化 (期間指定)',
	                        font: {
	                            size: 20
	                        }
	                    },
	                    tooltip: {
	                        callbacks: {
	                            label: function(context) {
	                                return `日付: ${context.label}, 気分: ${context.raw}`;
	                            }
	                        }
	                    }
	                },
	                scales: {
	                    x: {
	                        title: {
	                            display: true,
	                            text: '日付'
	                        }
	                    },
	                    y: {
	                        title: {
	                            display: true,
	                            text: '気分スコア'
	                        },
	                        max: 5,
	                        min: 1,
	                        ticks: {
	                            stepSize: 1
	                        }
	                    }
	                },
	                responsive: true
	            }
	        });
	    }
	</script>
       
    <!-- 期間選択フォーム -->
    <form action="ChartServlet" method="get">
        <input type="hidden" name="user_id" value="${userId}">
        <label for="start_date">開始日:</label>
        <input type="date" id="start_date" name="start_date" required>
        <label for="end_date">終了日:</label>
        <input type="date" id="end_date" name="end_date" required>
        <button type="submit">期間を適用</button>
    </form>
    
    <a href="./AdminServlet">管理者ページに戻る</a>
</body>
</html>
