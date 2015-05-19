rm test_running_var_n.dat
for ((i=10; i < 1000; i+=10));
do
    START=$(date +%s.%N)
    ./floyd < "testdata/var_n_$i.txt"
    END=$(date +%s.%N)
    DIFF=$(echo "$END - $START" | bc)
    echo -e "$i\t$DIFF" >> test_running_var_n_floyd.dat
done
