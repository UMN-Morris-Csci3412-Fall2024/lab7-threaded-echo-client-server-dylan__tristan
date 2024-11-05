# Experimental results

_Briefly (you don't need to write a lot) document the results of your
experiments with throwing a bunch of clients at your server, as described
in the lab write-up. You should probably delete or incorporate this text
into your write-up._

_You should indicate here what variations you tried (every connection gets
a new thread, using a threadpool of size X, etc., etc.), and what the
results were like when you spun up a bunch of threads that send
decent-sized files to the server._

Using 10 parallel threads on pumpkin.jpg it took <1 second
using 10 parallel threads on words.txt it took 1 second

Using 200 parallel threads on pumpkin.jpg it took 4 seconds
using 200 parallel threads on words.txt it took 5 seconds