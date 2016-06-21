#! /bin/sh
#
# Sprint boot application controlling script template
#

PRG="$0"

#configurations
PRGDIR=`dirname "$PRG"`
EXECUTABLE=app.war
PIDFILE=$PRGDIR/pid.txt

if [ -r "$PRGDIR/setenv.sh" ]; then
  . "$PRGDIR/setenv.sh"
fi

ARGS="--server.address=$TCP_LISTEN --server.port=$TCP_PORT $EXTRA_ARGS"

export HOME_DIR=$PRGDIR/data

# Better OS/400 detection: see Bugzilla 31132
os400=false
case "`uname`" in
OS400*) os400=true;;
esac

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

if test ! -f "$PRGDIR"/"$EXECUTABLE"; then
  echo "Cannot find $PRGDIR/$EXECUTABLE"
  exit 1
fi

case "$1" in
start)
  if test -f $PIDFILE; then
    pid=`cat "$PIDFILE"`
    echo 'Found PID file! Stopping old process...'
    kill $pid
    echo 'Old process '$pid' has been killed.'
  fi
  echo 'Starting application...'
  CMD="nohup java -jar ""$PRGDIR"/"$EXECUTABLE $ARGS"
  exec $CMD >/dev/null 2>&1&
  
  echo $!>$PIDFILE
  echo 'Application started successfully with PID '$!
    ERROR=$?
    ;;
stop)
  if test -f $PIDFILE; then
    pid=`cat "$PIDFILE"`
    echo 'Stopping application...'
      kill $pid
      if test -z $ERROR; then
        rm -f $PIDFILE
        echo 'Application stopped.'
      fi
  else
    echo 'PID file not found!'
  fi
  ERROR=$?
    ;;
*)
  echo 'Usage: run.sh start | run.sh stop'
  ;;
esac

exit $ERROR
