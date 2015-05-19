#!/usr/bin/gnuplot

set terminal pngcairo enhanced font 'Verdana,12'
set title "Floyd-Warshall running time"
set output 'floyd-given-data-running-time2.png'

set xlabel "input size"
set ylabel "running time per operation (s)"
set xtics autofreq
set grid ytics lc rgb "#bbbbbb" lw 1 lt 0
set grid xtics lc rgb "#bbbbbb" lw 1 lt 0

set xr [0:1000]
set key left top

f(x) = a*x**3
fit f(x) 'data/test_running_var_n_floyd.dat' via a

plot "data/test_running_var_n_floyd.dat" title 'Floyd-Warshall',\
     f(x) with lines title sprintf("%.5e x^3", a)


