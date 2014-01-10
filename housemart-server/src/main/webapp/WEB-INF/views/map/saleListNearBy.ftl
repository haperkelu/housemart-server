<script src="http://ditu.google.cn/maps?file=api&v=2.x&hl=zh-CN&key=AIzaSyBItBTVpc3EwAxES2U6x6NNrALw1tlPxRA"
type="text/javascript"></script>

<body>
	<div>
	<form action="/map/house/sale/detailedSearchNearby.controller" method="get">
		<input type="hidden" name="city" value="1">
	    纬度：<input type="text" value="${(lat)!'31.052933985705163'}" name="lat"/>
	    经度：<input type="text" value="${(lng)!'121.376953125'}" name="lng"/>
	    半径：<input type="text" value="${(range)!6000}" name="range"/>
	    </div>
	    <input type="submit"/>
    </form>
    <div id="mapContainer" style="height:600px; width:800px;display:block;"></div>
    </div>
</body>
<script type="text/javascript">
	var range = parseInt(${range!"6000"}/1000);
	var lat = parseFloat(${(lat)!'31.052933985705163'});
	var lng = parseFloat(${(lng)!'121.376953125'});
    var map = new GMap2(document.getElementById("mapContainer"));
    var point = new GLatLng(lat,lng);
    map.setCenter(point, 15);
    var marker = new GMarker(point);
    map.addOverlay(marker);
    draw(range, 36);

    var points = ${houseList};
    var avgLat = 0;
    var avgLng = 0;
    for (var index in points) {
        avgLat += parseFloat(points[index].lat);
        avgLng += parseFloat(points[index].lng);
        var point = new GLatLng(parseFloat(points[index].lat), parseFloat(points[index].lng));
        var marker = new GMarker(point);
        map.addOverlay(marker);
        var label = '<br>距离中心点: <span style="color:red;">' + parseFloat(points[index].distance) + '</span>米';
        var WINDOW_HTML = '<div style="width: 220px; padding-right: 10px"><span style="color:red;">' + points[index].residenceName + '</span>' + label + '</div>';
        marker.html = WINDOW_HTML;
        GEvent.addListener(marker, "click", function () {
            this.openInfoWindowHtml(this.html);
        });
    }

    ////pan and zoom to fit
    var bounds = new GLatLngBounds();

    function fit() {
        map.panTo(bounds.getCenter());
        map.setZoom(map.getBoundsZoomLevel(bounds));
    }

    //calling circle drawing function
    function draw(givenRad, givenQuality) {
        map.clearOverlays();
        bounds = new GLatLngBounds();
        var centre = map.getCenter()
        drawCircle(centre, givenRad, givenQuality);
        fit();
    }

    ////////////////////////// circle///////////////////////////////
    function drawCircle(center, radius, nodes, liColor, liWidth, liOpa, fillColor, fillOpa) {
        // Esa 2006
        //calculating km/degree
        var latConv = center.distanceFrom(new GLatLng(center.lat() + 0.1, center.lng())) / 100;
        var lngConv = center.distanceFrom(new GLatLng(center.lat(), center.lng() + 0.1)) / 100;

        //Loop
        var points = [];
        var step = parseInt(360 / nodes) || 10;
        for (var i = 0; i <= 360; i += step) {
            var pint = new GLatLng(center.lat() + (radius / latConv * Math.cos(i * Math.PI / 180)), center.lng() + (radius / lngConv * Math.sin(i * Math.PI / 180)));
            points.push(pint);
            bounds.extend(pint); //this is for fit function
        }
        fillColor = fillColor || liColor || "#0055ff";
        liWidth = liWidth || 2;
        var poly = new GPolygon(points, liColor, liWidth, liOpa, fillColor, fillOpa);
        map.addOverlay(poly);
    }
</script>
