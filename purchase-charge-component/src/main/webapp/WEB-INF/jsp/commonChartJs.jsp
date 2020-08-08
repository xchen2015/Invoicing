<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

    <script type='text/javascript' src="<c:url value='/common/js/highcharts.js' />"></script>
	<script type='text/javascript' src="<c:url value='/common/js/exporting.js' />"></script>
	<script type='text/javascript' src="<c:url value='/common/js/no-data-to-display.js' />"></script>
	<script type='text/javascript' src="<c:url value='/common/js/highcharts-3d.js' />"></script>
	
	<script type="text/javascript">
		Highcharts.setOptions({
			lang: {
				months: ['1月', '2月', '3月', '4月', '5月', '6月',  '7月', '8月', '9月',
				 '10月', '11月', '12月'],
				weekdays: ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
				downloadJPEG: '下载成JPEG图像',
				downloadPDF: '下载成PDF文档',
				downloadPNG: '下载成PNG图像',
				downloadSVG: '下载成SVG矢量图像',
				printChart: '打印图表',
				loading: '加载中...',
				contextButtonTitle: '图表上下文菜单'
			}
		});
	</script>
