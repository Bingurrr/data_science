### Collavorative Filtering
- Iten-Item Collaborative Filtering
- User-User Collaborative Filtering

### Factor Model 이란
사용자와 아이템을 요소들로 나타낼 수 있다고 보는 모델
사람들과 영화를 각각의 vectror(factor)로 표현을 할 수 있다.
어떤 스타일인지 파악

### Latent Factor Mode
사용자와 아이템을 잠재적인(Latent)요소 들로 나타낼 수 있다고 보는 모델
-> 성향이 비슷한것은 가까이 있고 성향이 반대인 것들은 멀리 있게 나타내는 것이 목표

### Matrix Factorization
- 사용자와 영화를 같은 차원의 공간에 매핑
-> 각각의 사용자와 영화를 같은 차원의 벡터로 표현한다.
-> 예측 결과가 실제 값과 비슷해지도록 최적화

Overfitting 주의
-> Reqularization을 해결한다(정규화)
Training Data에 너무 매몰되지 않도록, latent vector가 너무 큰 값을 갖는 경우 패넡티를 주자

### Global Baseline Estimate
각각의 특성들을 파악
예상점수 + bias(user) + bias(movie)
bias도 정규화를 해줘야한다.
