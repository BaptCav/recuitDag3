public int[][] matmin(int N, Graphe g) {
		int n = this.longueursAretes.length;
		double[][] tableau= g.longueursAretes;
		System.out.println(tableau[0][0]);
		double min;
		int[][] M= new int[n][N];
		for(int i=0;i<n;i++){
			for(int compteur=0;compteur<N;compteur++){
				min = 99999;
					if(compteur == 0) // initialisation pour remplir la première colonne, ligne i
					{
						for(int j=0;j<n;j++)
						{
						System.out.println("tableau["+i+"]["+j+"] = "+tableau[i][j]);
						 //on prend un très grand minimum
						if(tableau[i][j] != 0 && tableau[i][j] < min) //si la case courante est non nulle et qu'elle est inférieure a min
						{
							min = tableau[i][j]; // alors c'est un candidat potentiel ! et on recommence jusqu'à j=n-1
							M[i][0] = j; // et on stocke la valeur de l'index dans la matrice des indexs;
						}
						}
					}
					else // Maintenant, si on a déjà rempli la première colonne
					{
						min = 99999;
						for(int j=0;j<n;j++)
						{
						if(j != M[i][compteur-1])
						{
						System.out.println("je suis rentre et compteur = "+compteur);
						System.out.println("tableau["+i+"]["+j+"] = "+tableau[i][j]);
						System.out.println("tableau[i][M[i][compteur-1]] = "+tableau[i][M[i][compteur-1]]);
						System.out.println("min = "+min);
						if(tableau[i][j] != 0 && tableau[i][j] >= tableau[i][M[i][compteur-1]] && tableau[i][j] < min) // si la case courante est non nulle, qu'elle est plus grande ou égale au dernier minimum trouvé et qu'en plus elle est plus petite que le min...
						{
							min = tableau[i][j]; // alors c'est un candidat potentiel ! et on recommence jusqu'à j=n-1
							M[i][compteur] = j; // et on stocke la valeur de l'index dans la matrice des indexs
						}
						}
						}
					}
			}
		}
		System.out.println("Je dois retourner : 1 2 ou 1 4 peut etre");
		System.out.println("l'index 0 est "+M[0][0]);
		System.out.println("l'index 1 est "+M[0][1]);
		System.out.println("l'index 2 est "+M[0][2]);
		return M;
	}
