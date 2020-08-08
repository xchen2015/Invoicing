<% response.setContentType ("text/css"); %>

.upld-multiple .GWTUpld .GWTCProgress, .upld-multiple .GWTUpld .filename
{
	font-weight: normal;
}
#dlg-view-goods-photo #previewPicture li {
	float: left;
	height: 200px;
	padding: 2px;
}

#dlg-view-goods-photo #previewPicture li img {
	height: 100%;
	cursor: pointer;
}
.imgDiv {
	position: relative;
	height: 100%;
}

.imgDiv .imgControl {
	display: none;
	width: 100%;
}

.imgDiv:hover .imgControl {
	display: block;
	position: absolute;
	top: 0;
}

.imgDiv:hover .imgControl .del {
	background: rgb(0, 235, 255);
	font-size: 14px;
	padding: 0 6px;
	position: absolute;
	top: 2px;
	color: #fff;
	right: 2px;
	cursor: pointer;
}

#uploadPictureBtn a 
{
	color: blue;
	text-decoration: underline;
	cursor: pointer;
	white-space: nowrap;
	font-size: 10px;
}
#uploadPictureBtn a:hover 
{
	color: #af6b29;
}