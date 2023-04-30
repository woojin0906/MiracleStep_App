<?php
    /*
      OrganizLogin.php : 기업 로그인 처리
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "비밀번호", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

/* (2) 앱에서 작성한 아이디와 비밀번호를 가져옴. */
    $oId = isset($_POST["id"]) ? $_POST["id"] : "";
    $oPw = isset($_POST["pw"]) ? $_POST["pw"] : "";

/* (3) 현재 DB에 저장된 아이디와 비밀번호를 검색함. */  // ss는 String이 두 개라서 ss가 들어감.
    $statement = mysqli_prepare($con, "SELECT id FROM Organization WHERE id = ? AND pw = ?;");
    mysqli_stmt_bind_param($statement, "ss", $oId, $oPw);
    mysqli_stmt_execute($statement);

/* (4) DB안에 해당 아이디와 비밀번호가 일치하면 User테아블 정보 다 가져오기. */
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $oId);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["id"] = $oId;
    }

/* (5) 로그인 실행 결과 전송. */
    echo json_encode($response);
?>