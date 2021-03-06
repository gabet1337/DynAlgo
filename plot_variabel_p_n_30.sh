#!/usr/bin/gnuplot

set terminal pngcairo enhanced font 'Verdana,12'
set title "Runs before an error occuring"
set output 'error-probability2.png'

set xlabel "prime p"
set ylabel "average runs before error"
set xtics autofreq
set grid ytics lc rgb "#bbbbbb" lw 1 lt 0
set grid xtics lc rgb "#bbbbbb" lw 1 lt 0

set key left top
set format x "%.0t*10^{%T}"

f(x) = (1/a**3) * x
fit f(x) 'data/var_p_n_30.dat' via a

plot "data/var_p_n_30.dat" title "Runs before error" pointtype 7,\
     f(x) with lines title sprintf("1/%.3f^3 x", a)



