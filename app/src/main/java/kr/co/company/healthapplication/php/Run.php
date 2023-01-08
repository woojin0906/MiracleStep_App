<?php
    /*
      // 파일 공유를 위해 올립니다. (2023-01-03 이수.)

      Register.php : 회원가입을 처리하는 php
      $ : 변수를 선언해주는 기호
      con : mysql 연결을 시도하는 변수.
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    // date타입을 php는 String으로 받아들이기 때문에 형변환이 필요해요... (2023-01-03 이수)
    $runDate = isset($_POST["runDate"]) ? $_POST["runDate"] : "";
    //$runDate = str_replace(".","-",$runDate);
    //$runDate = str_replace("/","-",$runDate);
    //$runDate = date('Ymd', strtotime($runDate));

    $runTime = isset($_POST["runTime"]) ? $_POST["runTime"] : "";
    $runDistance = isset($_POST["runDistance"]) ? $_POST["runDistance"] : "";
    $runStep = isset($_POST["runStep"]) ? $_POST["runStep"] : "";
    $runKcal = isset($_POST["runKcal"]) ? $_POST["runKcal"] : "";

/* (3) DB안에 insert하는 문장. (User, UserInfo) */
    $statement = mysqli_prepare($con, "INSERT INTO Running VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssidid", $userID, $runDate, $runTime, $runDistance, $runStep, $runKcal);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($runDate);
    echo json_encode($response);
?>
