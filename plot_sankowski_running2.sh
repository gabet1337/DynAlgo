#!/usr/bin/gnuplot

set terminal pngcairo enhanced font 'Verdana,12'
set title "Sankowski running time"
set output 'sankowski-given-data-running-time2.png'

set xlabel "input size"
set ylabel "running time (s)"
set xtics autofreq
set grid ytics lc rgb "#bbbbbb" lw 1 lt 0
set grid xtics lc rgb "#bbbbbb" lw 1 lt 0

set xr [0:1100]
set key left top

f(x) = (m*x**2)*(log(x)/log(2))
fit f(x) 'data/test_running_var_n_sankowski.dat' via m

plot "data/test_running_var_n_sankowski.dat" title 'Sankowski',\
     f(x) with lines title sprintf("%.3Ex^2 log_2(x)",m)

