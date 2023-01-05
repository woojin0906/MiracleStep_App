<?php
    /*
      Login.php : 로그인을 처리하는 php (POST 형식으로 MySQL로부터 데이터를 받아옴.)
      con : mysql 연결을 시도하는 변수.
      "$변수" 로 변수 선언
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호를 입력할 자리입니다.(깃허브 올릴 때에는 안적을께요~)", "miraclestep"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

/* (2) 앱에서 작성한 아이디와 비밀번호를 가져옴. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $userPassword = isset($_POST["userPassword"]) ? $_POST["userPassword"] : "";

/* (3) 현재 DB에 저장된 아이디와 비밀번호를 검색함. */  // ss는 String이 두 개라서 ss가 들어감.
    $statement = mysqli_prepare($con, "SELECT * FROM User WHERE UserID = ? AND UserPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);

/* (4) DB안에 해당 아이디와 비밀번호가 일치하면 User테아블 정보 다 가져오기. */
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword, $userName, $userPhoneNumber, $userDstep);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;
        $response["userName"] = $userName;
        $response["userPhoneNumber"] = $userPhoneNumber;
        $response["userDstep"] = $userDstep;
    }

/* (5) 로그인 실행 결과 전송. */
    echo json_encode($response);
?>
