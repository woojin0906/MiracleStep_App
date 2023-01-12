<?php
    /*
      CheckListChecked.php : 홈의 체크리스트 클릭 이벤트를 처리하는 php (POST 형식으로 MySQL로부터 데이터를 받아옴.)
      con : mysql 연결을 시도하는 변수.
      "$변수" 로 변수 선언
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

/* (2) 앱에서 선택한 카테고리를 가져옴. */
    //$cate = isset($_GET["category"]) ? $_GET["category"] : "TotalUserStep";

    // userID, nowDate

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $SDate = isset($_POST["nowDate"]) ? $_POST["nowDate"] : "";
ㄴ
/* (3) 현재 DB에 저장된 사용자들의 id와 카테고리의 값을 검색함. */
    $statement = mysqli_prepare($con, "SELECT ListNum, Checked, CheckContent FROM Schedule WHERE Id = ? AND SDate = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $SDate);
    mysqli_stmt_execute($statement);

// /* (4) DB안에 해당 카테고리가 일치하는 Rank정보 가져오기 정보 가져오기. */
    mysqli_stmt_store_result($statement);
         mysqli_stmt_bind_result($statement, $ListNum, $Checked, $CheckContent);

       $response = array();

     $allrevs = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {

        $response["success"] = true;
        $response["ListNum"] = $ListNum;
        $response["Checked"] = $Checked;
        $response["CheckContent"] = $CheckContent;
        $allrevs[] = $response;
    }

/* (5) 로그인 실행 결과 전송. */
echo json_encode($allrevs);
?>