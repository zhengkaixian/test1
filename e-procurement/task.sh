#!/bin/bash
function myHelp()
{
    echo "--------------------------------------------------"
    echo "输入 task.sh ccf dev"
    echo "    修改src/main/resources/application.yml配置文件"
    echo "          active: dev"
    echo "--------------------------------------------------"
    echo "输入 task.sh kill graph-1.0.jar"
    echo "     杀死 graph-1.0.jar 程序"
    echo "--------------------------------------------------"
    echo "输入 task.sh run graph-1.0.jar"
    echo "      运行 graph-1.0.jar 程序"
    echo "--------------------------------------------------"
    echo "输入 task.sh build 1.0.0"
    echo "      使用 gradlew 打包程序 打包版本为1.0。0 "
    echo "--------------------------------------------------"
    echo "输入 task.sh cdc 192.168.64.137:1500/neo4j-demo:latest"
    echo "      修改 ./docker/docker-compose.yml 中的 image"
    echo "--------------------------------------------------"
}
# Gradlew 构建
function build()
{
    name=$1
    version=$2
    echo "chmod +x gradlew"
    chmod +x gradlew
    echo "./gradlew :$name:build -x test -P JAR_VERSION=$version"
    ./gradlew :$name:build -x test -P JAR_VERSION=$version
}

# Gradlew 构建
function mavenBuild()
{
    echo "mvn install -Dmaven.test.skip=true"
    mvn install -Dmaven.test.skip=true
}
# Gradle 构建
function gradleBuild()
{
    name=$1
    version=$2
    echo "gradle :$name:build -x test -P JAR_VERSION=$version"
    gradle :$name:build -x test -P JAR_VERSION=$version
}
# 修改 SpringBoot 配置文件
function changerConfigFile()
{
    value=$1
    echo "sed -i 's/\(active: \)[a-z]*/\1${value}/g' ./src/main/resources/application.yml"
    sed -i 's/\(active: \)[a-z]*/\1'${value}'/g' ./src/main/resources/application.yml
}
# 修改 docker-compose 配置文件
function changerDockerCompose()
{
    value=$1
    echo "sed -i 's/\(image: \).*/\1${value}/g' ./docker-compose.yml"
    sed -i 's?\(image: \).*?\1'${value}'?g' ./docker-compose.yml
}
# 根据名字停止指定进程
function myKill()
{
    name=$1
    echo "Kill the $name"
    PROCESS=$(ps -ef|grep -v $0|grep -v grep |grep $name | awk '{print $2}')
    echo "Kill the pid[$PROCESS]"
    #杀死对应进程
    if [ -z "$PROCESS" ];then
    	echo "\$PROCESS is null"
    else
    	for pid in $PROCESS
    	do
    		echo "Pid is :$pid"
    		kill -9 $pid
    	done
    fi
}
# 根据 pid 文件 杀死对应程序
function killByPidFile()
{
    name=$1
    if [ ! -f "${name}.pid" ];then
      echo "没有找到对应 pid 文件"
    else
      pid=$(cat ${name}.pid | awk -F '=' '{print $2}' | sed -n '1p')
      pid=$(ps -ax | awk '{ print $1 }' | grep -e "^${pid}$")
      if [ -z "$pid" ];then
        echo "\$pid is null"
      else
        echo "Pid is : $pid"
        kill -9 $pid
      fi
    fi
}
# 运行指定 jar 包,并将运行日志输出到当前目录中
function myRun()
{
    name=$2
    # 备份原先的日志文件
    yes|cp ${1%.*}.log ${1%.*}.log.bak
    # 在 jenkins 中后台执行命令(&) 需要后执行后在执行有输出的命令
    nohup java -Xms4048m -Xmx4048m -jar $1 > ${1%.*}.log 2>&1 &
    # 将程序pid 写入 .pid 文件内
    echo "pid=$!" > "$name.pid"
    # 以下两条命令,不可以删除
    # 删除的后果是nohup java -Xms4048m -Xmx4048m -jar $1 > ${1%.*}.log 2>&1 & 执行无效
    sleep 20
    # 输出当前 jar 包程序日志的前1000条
    tail -n 1000 ${1%.*}.log
}
# jar 文件并且重命名---原先的名字 + 文修改时间
function backup()
{
    if [ ! -f "${1}" ];then
        echo "备份失败,没有找到备份文件"
    else
        backupFile=$1
        backupFolder=$2
        # resourceCatalog_1.0.0.jar
        # 会截取上面的字符串中最后一个 . 符号后的字符串
        # 最终得到 resourceCatalog_1.0.0
        name="${1%.*}"
        date1=`stat ${backupFile} | grep Modify | awk '{print $2}'`
        date2=`stat ${backupFile} | grep Modify | awk '{print $3}'`
        # 15:10:11.229943211 去除纳秒数得到15:10:11
        date2="${date2%.*}"
        targetBackupFile="${name}_${date1//[\.:-]/}_${date2//[\.:-]/}.jar"
        if [ ! -d "${backupFolder}" ]; then
          mkdir -p ${backupFolder}
        fi
        echo "mv ${backupFile} ${backupFolder}/${targetBackupFile}"
        mv ${backupFile} ${backupFolder}/${targetBackupFile}
        #删除备份文件夹内的备份文件（只保留最新的5个备份文件）
        cd ${backupFolder}
        rmFile .jar 5
        echo "备份成功"
     fi
}
# 批量删除指定文件,只保留指定数量的最新文件
function rmFile()
{
    fileName=$1
    keepNum=$2
    # 查询指定文件名
    jarFiles=$(ls -lt | grep $fileName |awk '{print $9}')
    echo "jar file list [$jarFiles]"
    if [ -z "$jarFiles" ];then
    	echo "\$jarFiles is null"
    else
        fileNum=0
    	for jarFile in $jarFiles
    	do
            ((fileNum=fileNum+1))
    		echo "jar file is :$jarFile"
            if [ $fileNum -gt $keepNum ];then
                echo "yes|rm $jarFile"
                yes|rm $jarFile
            fi
    	done
    fi
}
task_name=$1
echo Task name is $1
case $task_name in
    help)
        myHelp
    ;;
    build)
        echo "task.sh build $2 $3"
        build $2 $3
    ;;
    gradleBuild)
        echo "task.sh gradleBuild $2 $3"
        gradleBuild $2 $3
    ;;
    ccf)
        echo "task.sh changerConfigFile $2"
        changerConfigFile $2
    ;;
    kill)
        echo "task.sh kill $2"
        myKill $2
    ;;
    killByPid)
        echo "task.sh killByPidFile $2"
        killByPidFile $2
    ;;
    run)
        echo "task.sh run $2 $3"
        myRun $2 $3
    ;;
    cdc)
        echo "task.sh changerDockerCompose $2"
        changerDockerCompose $2
    ;;
    backup)
        echo "task.sh backup $2 $3"
        backup $2 $3
    ;;
    mavenBuild)
      echo "task.sh mavenBuild"
      mavenBuild
    ;;
    *)
        echo "This is Default"
esac