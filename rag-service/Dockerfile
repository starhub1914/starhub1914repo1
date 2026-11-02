FROM python:3.11-slim-bookworm

ENV PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1 \
    FAISS_INDEX_PATH=/app/faiss_index

WORKDIR /app
RUN apt-get update \
    && apt-get install -y --no-install-recommends libgomp1 \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --system --gid 10001 rag \
    && useradd --system --uid 10001 --gid rag --home-dir /app rag \
    && mkdir -p /app/faiss_index \
    && chown -R rag:rag /app

COPY requirements.txt ./requirements.txt
RUN pip install --no-cache-dir -r requirements.txt
COPY --chown=rag:rag app.py ./app.py

USER 10001:10001
EXPOSE 5000
CMD ["gunicorn", "--bind", "0.0.0.0:5000", "--workers", "1", "--threads", "4", "--timeout", "120", "app:app"]