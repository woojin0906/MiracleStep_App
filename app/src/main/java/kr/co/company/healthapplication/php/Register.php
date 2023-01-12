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
    $userPassword = isset($_POST["userPassword"]) ? $_POST["userPassword"] : "";
    $userName = isset($_POST["userName"]) ? $_POST["userName"] : "";

    // 전화번호를 php는 int로 받아들여서 String으로 변환이 필요해요... (2023-01-03 이수)
    (string)$UserPhoneNumber = isset($_POST["UserPhoneNumber"]) ? $_POST["UserPhoneNumber"] : "";
    $userDstep = 0;

    // date타입을 php는 String으로 받아들이기 때문에 형변환이 필요해요... (2023-01-03 이수)
    $UserInfoDate = isset($_POST["UserInfoDate"]) ? $_POST["UserInfoDate"] : "";
    //$UserInfoDate=str_replace(".","-",$UserInfoDate);
    //$UserInfoDate=str_replace("/","-",$UserInfoDate);
    //$UserInfoDate = date('Ymd', strtotime($UserInfoDate));

    $userHeight = isset($_POST["userHeight"]) ? $_POST["userHeight"] : "";
    $userWeight = isset($_POST["userWeight"]) ? $_POST["userWeight"] : "";

    $userBirth = isset($_POST["userBirth"]) ? $_POST["userBirth"] : "";
    //$userBirth=str_replace(".","-",$userBirth);
    //$userBirth=str_replace("/","-",$userBirth);
    //$userBirth = date('Ymd', strtotime($userBirth));
    
    $userImg = "img";

/* (3) DB안에 insert하는 문장. (User, UserInfo) */
    $statement1 = mysqli_prepare($con, "INSERT INTO User VALUES (?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement1, "ssssiss", $userID, $userPassword, $userName, $UserPhoneNumber, $userDstep, $userImg, $userBirth);
    mysqli_stmt_execute($statement1);

    $statement2 = mysqli_prepare($con, "INSERT INTO UserInfo VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement2, "ssdd", $userID, $UserInfoDate, $userHeight, $userWeight);
    mysqli_stmt_execute($statement2);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);

?>
