let index = {
	init: function() {
		$("#btn-save").on("click", () => { //function(){} 대신에 ()=>{}는 this를 바인딩하기 위해서이다!
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
		$("#btn-update").on("click", () => { 
			this.update();
		});
		//스프링 시큐리티로 로그인하려고 아래 주석처리
		//$("#btn-login").on("click", () => { //function(){} 대신에 ()=>{}는 this를 바인딩하기 위해서이다!
			//this.login();
		//});
	},
	
	//게시글 작성
	save: function() {
		//alert('user의 save 함수 호출됨');
		let data = {
			title : $("#title").val(),
			content : $("#content").val()
		};
		
		//console.log(data);        //자바스크립트 오브젝트
		//console.log(JSON.stringify(data));    // JSON 문자열
		
		//ajax 호출시 default가 비동기 호출
		 //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!!
		 //ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
		$.ajax({
			type:"POST",
			url:"/api/board",
			data:JSON.stringify(data), //위에 data는 자바스크립트 객체이다. 따라서 자바가 이해하기 위해서 json으로 변경을 해줘야한다.
			contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
			dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 String(문자열)임. 생긴게 json이라면 => javascriptt 오브젝트로 변환
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다");
			//console.log(resp);
			location.href="/"; //메인페이지로 이동
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
	//게시글 삭제
	deleteById: function() {	
		let id = $("#id").text();
		
		if(confirm('정말 삭제 하시겠습니까?')) //확인 누르면 true, 취소 누르면 false
		{		
			$.ajax({
				type:"DELETE",
				url:"/api/board/"+id,
				dataType:"json" 
			}).done(function(resp){
				alert("삭제가 완료되었습니다");
				location.href="/"; 
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
		else{
			return false;
		}
	},
	
	//게시글 수정
	update: function() {
		let id = $("#id").val();
		
		let data = {
			title : $("#title").val(),
			content : $("#content").val()
		};
		if(confirm('정말 수정 하시겠습니까?')) 
		{
			$.ajax({
				type:"PUT",
				url:"/api/board/"+id,
				data:JSON.stringify(data), 
				contentType:"application/json; charset=utf-8", 
				dataType:"json" 
			}).done(function(resp){
				alert("글수정이 완료되었습니다");
				location.href="/"; 
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
		else{
			return false;			
		}	
	},
	
	
	//스프링 시큐리티로 로그인하려고 아래 주석처리
	//login: function() {
		//alert('user의 save 함수 호출됨');
		//let data = {
			//username : $("#username").val(),
			//password : $("#password").val()
		//};

		//$.ajax({
			//type:"POST",
			//url:"/api/user/login",
			//data:JSON.stringify(data), //위에 data는 자바스크립트 객체이다. 따라서 자바가 이해하기 위해서 json으로 변경을 해줘야한다.
			//contentType:"application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
			//dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 String(문자열)임. 생긴게 json이라면 => javascriptt 오브젝트로 변환
		//}).done(function(resp){
			//alert("로그인이 완료되었습니다");
			//console.log(resp);
			//location.href="/"; //메인페이지로 이동
		//}).fail(function(error){
			//alert(JSON.stringify(error));
		//}); 
	//}
}

index.init();